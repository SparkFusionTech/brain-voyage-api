package com.sparkfusion.quiz.brainvoyage.api.online.handler;

import com.sparkfusion.quiz.brainvoyage.api.dto.question.GetQuestionWithAnswerDto;
import com.sparkfusion.quiz.brainvoyage.api.dto.user.GetUserDto;
import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import com.sparkfusion.quiz.brainvoyage.api.exception.UserNotFoundException;
import com.sparkfusion.quiz.brainvoyage.api.online.command.Responses;
import com.sparkfusion.quiz.brainvoyage.api.online.state.*;
import com.sparkfusion.quiz.brainvoyage.api.online.state.connection.OpponentModel;
import com.sparkfusion.quiz.brainvoyage.api.online.state.connection.PlayerConnectedModel;
import com.sparkfusion.quiz.brainvoyage.api.online.state.connection.RoomIdModel;
import com.sparkfusion.quiz.brainvoyage.api.service.QuestionService;
import com.sparkfusion.quiz.brainvoyage.api.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

@Component
public class GameRoomHandler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> answerTimeoutFuture;
    private ScheduledFuture<?> resultTimeoutFuture;

    private static final int ANSWER_TIMEOUT_SECONDS = 20;
    private static final int RESULT_TIMEOUT_SECONDS = 10;

    private final UserService userService;
    private final QuestionService questionService;

    private final GameMessageSender gameMessageSender;

    public GameRoomHandler(
            UserService userService,
            QuestionService questionService,
            GameMessageSender gameMessageSender
    ) {
        this.userService = userService;
        this.questionService = questionService;
        this.gameMessageSender = gameMessageSender;
    }

    public void createOrJoinRoom(
            Map<String, GameRoom> rooms,
            WebSocketSession session,
            JoinRoomUserInfo joinRoomUserInfo
    ) {
        System.out.println(joinRoomUserInfo.toString());
        GetUserDto user;
        try {
            user = userService.readUser(joinRoomUserInfo.getEmail());
        } catch (UserNotFoundException e) {
            gameMessageSender.sendResponse(session, Responses.USER_NOT_FOUND, null);
            return;
        } catch (UnexpectedException e) {
            gameMessageSender.sendResponse(session, Responses.UNEXPECTED, null);
            return;
        }

        Player player = new Player(session, user.getId(), user.getName(), user.getIconUrl());

        for (Map.Entry<String, GameRoom> entries : rooms.entrySet()) {
            GameRoom room = entries.getValue();
            System.out.println(room.toString());
            if (room.hasPlayer(session, user.getId())) {
                gameMessageSender.sendResponse(session, Responses.PLAYER_IN_GAME, new RoomIdModel(entries.getKey()));
                return;
            }
        }

        for (Map.Entry<String, GameRoom> entries : rooms.entrySet()) {
            GameRoom room = entries.getValue();
            if (room.needAPlayer() && room.equalsCatalog(joinRoomUserInfo.getCatalogId())) {
                System.out.println("NEED A PLAYER");
                Boolean result = room.connectNewPlayer(player);
                if (result) {
                    Player secondPlayer = room.getSecondPlayer(player.getSession());
                    if (secondPlayer == null) {
                        room.disconnectPlayer(player.getSession());
                        rooms.remove(entries.getKey());
                        gameMessageSender.sendResponse(player.getSession(), Responses.FAILURE_PLAYER_CONNECTION, null);
                        return;
                    }

                    OpponentModel firstOpponentModel = new OpponentModel(secondPlayer.getId(), secondPlayer.getName(), secondPlayer.getIconUrl());
                    OpponentModel secondOpponentModel = new OpponentModel(player.getId(), player.getName(), player.getIconUrl());

                    PlayerConnectedModel firstPlayerConnectedModel = new PlayerConnectedModel(entries.getKey(), firstOpponentModel);
                    PlayerConnectedModel secondPlayerConnectedModel = new PlayerConnectedModel(entries.getKey(), secondOpponentModel);

                    gameMessageSender.sendResponse(player.getSession(), Responses.PLAYER_CONNECTED, firstPlayerConnectedModel);
                    gameMessageSender.sendResponse(secondPlayer.getSession(), Responses.PLAYER_CONNECTED, secondPlayerConnectedModel);

                    startGamePreparation(room, rooms);
                } else {
                    gameMessageSender.sendResponse(player.getSession(), Responses.FAILURE_PLAYER_CONNECTION, null);
                }

                return;
            }
        }

        String roomId = GameRoom.generateGameRoomId();
        GameRoom gameRoom = new GameRoom(roomId, player, joinRoomUserInfo.getCatalogId());
        rooms.put(roomId, gameRoom);

        RoomIdModel roomIdModel = new RoomIdModel(roomId);
        gameMessageSender.sendResponse(player.getSession(), Responses.ROOM_CREATED, roomIdModel);
    }

    public void disconnectPlayer(
            Map<String, GameRoom> rooms,
            WebSocketSession session
    ) {
        for (Map.Entry<String, GameRoom> entries : rooms.entrySet()) {
            GameRoom room = entries.getValue();
            if (!room.hasPlayer(session)) continue;

            room.disconnectPlayer(session);
            if (room.isGameFinished()) {
                gameMessageSender.sendResponse(session, Responses.DISCONNECTED, null);
                removeRoom(rooms, entries.getKey());
            } else {
                Player secondPlayer = room.getSecondPlayer(session);
                if (secondPlayer == null) {
                    gameMessageSender.sendResponse(session, Responses.DISCONNECTED, null);
                    removeRoom(rooms, entries.getKey());
                    return;
                }

                gameMessageSender.sendResponse(session, Responses.DISCONNECTED, null);
                GameFinishedModel.PlayerModel player = new GameFinishedModel.PlayerModel(
                        secondPlayer.getId(),
                        secondPlayer.getName(),
                        secondPlayer.getIconUrl(),
                        secondPlayer.getScore()
                );
                gameMessageSender.sendResponse(secondPlayer.getSession(), Responses.OPPONENT_DISCONNECTED, player);
                removeRoom(rooms, entries.getKey());
            }

            return;
        }

        gameMessageSender.sendResponse(session, Responses.PLAYER_NOT_IN_GAME, null);
    }

    public void answerOnQuestion(
            Map<String, GameRoom> rooms,
            WebSocketSession session,
            AnswerOnQuestionModel answerOnQuestionModel
    ) {
        for (Map.Entry<String, GameRoom> entries : rooms.entrySet()) {
            GameRoom room = entries.getValue();
            if (!room.hasPlayer(session)) continue;

            Player currentPlayer = room.getPlayerBySession(session);
            if (currentPlayer == null || Objects.requireNonNull(currentPlayer).getAnswered()) return;
            room.setAnsweredValue(session);

            Player secondPlayer = room.getSecondPlayer(session);
            if (Objects.requireNonNull(secondPlayer).getAnswered()) {
                gameMessageSender.sendResponse(session, Responses.WAITING_NEXT_QUESTION, null);
                gameMessageSender.sendResponse(secondPlayer.getSession(), Responses.WAITING_NEXT_QUESTION, null);
                scheduleResultTimeout(room, rooms);
            } else {
                room.addScoreToPlayer(session, answerOnQuestionModel.getScore(), true);
                OpponentModel opponent = new OpponentModel(secondPlayer.getId(), secondPlayer.getName(), secondPlayer.getIconUrl());
                gameMessageSender.sendResponse(session, Responses.WAITING_OPPONENT_ANSWER, opponent);
            }

            return;
        }
    }

    public void nextQuestion(
            Map<String, GameRoom> rooms,
            WebSocketSession session
    ) {
        for (Map.Entry<String, GameRoom> entries : rooms.entrySet()) {
            GameRoom room = entries.getValue();
            if (!room.hasPlayer(session)) continue;

            System.out.println(0);
            Player currentPlayer = room.getPlayerBySession(session);
            System.out.println(currentPlayer);
            if (currentPlayer == null || Objects.requireNonNull(currentPlayer).getClickNext()) return;
            room.setNextClicked(session);

            System.out.println(1);
            Player secondPlayer = room.getSecondPlayer(session);
            if (Objects.requireNonNull(secondPlayer).getClickNext()) {
                if (resultTimeoutFuture != null && !resultTimeoutFuture.isDone()) {
                    resultTimeoutFuture.cancel(false);
                }

                System.out.println("HERE");
                handleResultTimeout(room, rooms);
            }

            System.out.println(2);
            break;
        }
    }

    private void startGamePreparation(GameRoom room, Map<String, GameRoom> rooms) {
        CompletableFuture.supplyAsync(() -> {
            try {
                List<GetQuestionWithAnswerDto> questions = questionService.readQuestionsWithAnswersForOnlineGameByCategoryId(room.getCatalogId());
                room.setGameQuestions(questions);
                return questions.get(0);
            } catch (UnexpectedException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }).thenAccept(firstQuestion -> {
            if (firstQuestion != null) {
                gameMessageSender.sendResponse(
                        Objects.requireNonNull(room.getPlayer1()).getSession(),
                        Responses.GAME_START,
                        firstQuestion
                );
                gameMessageSender.sendResponse(
                        Objects.requireNonNull(room.getPlayer2()).getSession(),
                        Responses.GAME_START,
                        firstQuestion
                );

                scheduleAnswerTimeout(room, rooms);
            } else {
                gameMessageSender.sendResponse(
                        Objects.requireNonNull(room.getPlayer1()).getSession(), Responses.GAME_ERROR, null
                );
                gameMessageSender.sendResponse(
                        Objects.requireNonNull(room.getPlayer2()).getSession(), Responses.GAME_ERROR, null
                );

                removeRoom(rooms, room.getId());
            }
        });
    }

    private void scheduleAnswerTimeout(GameRoom room, Map<String, GameRoom> rooms) {
        if (answerTimeoutFuture != null && !answerTimeoutFuture.isDone()) {
            answerTimeoutFuture.cancel(false);
        }

        answerTimeoutFuture = scheduler.schedule(() -> handleAnswerTimeout(room, rooms), ANSWER_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    private void handleAnswerTimeout(GameRoom room, Map<String, GameRoom> rooms) {
        if (!Objects.requireNonNull(room.getPlayer1()).getAnswered()) {
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer1()).getSession(),
                    Responses.ANSWER_TIMEOUT, null
            );
        } else {
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer1()).getSession(),
                    Responses.WAITING_NEXT_QUESTION, null
            );
        }

        if (!Objects.requireNonNull(room.getPlayer2()).getAnswered()) {
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer2()).getSession(),
                    Responses.ANSWER_TIMEOUT, null
            );
        } else {
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer2()).getSession(),
                    Responses.WAITING_NEXT_QUESTION, null
            );
        }

        scheduleResultTimeout(room, rooms);
    }

    private void scheduleResultTimeout(GameRoom room, Map<String, GameRoom> rooms) {
        if (resultTimeoutFuture != null && !resultTimeoutFuture.isDone()) {
            resultTimeoutFuture.cancel(false);
        }

        resultTimeoutFuture = scheduler.schedule(() -> handleResultTimeout(room, rooms), RESULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    private void handleResultTimeout(GameRoom room, Map<String, GameRoom> rooms) {
        room.clearNextClicked();
        room.clearAnsweredValues();
        GetQuestionWithAnswerDto question = room.getNextQuestion();
        if (question == null) {
            GameFinishedModel gameFinishedModel = GameFinishedModel.initModel(room);
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer1()).getSession(), Responses.GAME_END, gameFinishedModel
            );
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer2()).getSession(), Responses.GAME_END, gameFinishedModel
            );

            removeRoom(rooms, room.getId());
        } else {
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer1()).getSession(), Responses.NEXT_QUESTION, question
            );
            gameMessageSender.sendResponse(
                    Objects.requireNonNull(room.getPlayer2()).getSession(), Responses.NEXT_QUESTION, question
            );

            scheduleAnswerTimeout(room, rooms);
        }
    }

    private void removeRoom(Map<String, GameRoom> rooms, String key) {
        if (answerTimeoutFuture != null && !answerTimeoutFuture.isDone()) {
            answerTimeoutFuture.cancel(false);
        }
        if (resultTimeoutFuture != null && !resultTimeoutFuture.isDone()) {
            resultTimeoutFuture.cancel(false);
        }

        rooms.remove(key);
    }
}































