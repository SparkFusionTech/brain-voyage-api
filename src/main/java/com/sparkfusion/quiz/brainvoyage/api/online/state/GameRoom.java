package com.sparkfusion.quiz.brainvoyage.api.online.state;

import com.sparkfusion.quiz.brainvoyage.api.dto.question.GetQuestionWithAnswerDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameRoom {

    @NotNull
    private final String id;

    @Nullable
    private final Long catalogId;

    @Nullable
    private Player player1;

    @Nullable
    private Player player2;

    private Integer currentQuestionId = 0;

    private final List<GetQuestionWithAnswerDto> answers = new ArrayList<>(10);

    public GameRoom(@NotNull String id, @NotNull Player player, @Nullable Long catalogId) {
        this.id = id;
        this.player1 = player;
        this.catalogId = catalogId;
    }

    public void setNextClicked(@NotNull WebSocketSession session) {
        Player player = getPlayerBySession(session);
        if (player != null) {
            player.setClickNext(true);
        }
    }

    public void clearNextClicked() {
        if (player1 != null) player1.setClickNext(false);
        if (player2 != null) player2.setClickNext(false);
    }

    @Nullable
    public GetQuestionWithAnswerDto getNextQuestion() {
        if (currentQuestionId == answers.size() - 1) return null;
        currentQuestionId++;
        return answers.get(currentQuestionId - 1);
    }

    public void clearAnsweredValues() {
        if (player1 != null) player1.setAnswered(false);
        if (player2 != null) player2.setAnswered(false);
    }

    public void addScoreToPlayer(@NotNull WebSocketSession session, Integer score, Boolean isFirst) {
        Player player = getPlayerBySession(session);
        if (player != null) {
            player.addScore(score);
            if (isFirst) player.addScore(score * 20 / 100);
        }
    }

    public void setAnsweredValue(@NotNull WebSocketSession session) {
        Player player = getPlayerBySession(session);
        if (player != null) player.setAnswered(true);
    }

    public void setGameQuestions(List<GetQuestionWithAnswerDto> answers) {
        if (this.answers.isEmpty()) {
            this.answers.addAll(answers);
        }
    }

    public synchronized void disconnectPlayer(
            WebSocketSession session
    ) {
        if (player1 != null && player1.getSession().equals(session)) {
            player1.setConnected(false);
            return;
        }

        if (player2 != null && player2.getSession().equals(session)) {
            player2.setConnected(false);
        }
    }

    public synchronized Boolean connectNewPlayer(
            Player player
    ) {
        if (isPlayerConnected(player.getSession())) return false;
        if (player1 != null && player2 != null) return false;
        if (player1 == null) {
            player1 = player;
        } else {
            player2 = player;
        }

        return true;
    }

    @Nullable
    public Player getPlayerBySession(@NotNull WebSocketSession session) {
        if (player1 == null && player2 == null) return null;
        if (player1 == null && player2.getSession().equals(session)) {
            return player2;
        }

        if (player2 == null && player1.getSession().equals(session)) {
            return player1;
        }

        return null;
    }

    public Boolean hasPlayer(@NotNull WebSocketSession session, Long id) {
        if (player1 != null && player1.getConnected() && (player1.getId().equals(id) || player1.getSession().equals(session))) {
            return true;
        }

        return player2 != null && player2.getConnected() && (player2.getId().equals(id) || player2.getSession().equals(session));
    }

    public Boolean hasPlayer(@NotNull WebSocketSession session) {
        if (player1 != null && player1.getConnected() && player1.getSession().equals(session)) {
            return true;
        }

        return player2 != null && player2.getConnected() && player2.getSession().equals(session);
    }

    @Nullable
    public Player getSecondPlayer(@NotNull WebSocketSession session) {
        if (player1 != null && player1.getConnected() && !player1.getSession().equals(session)) {
            return player1;
        }

        if (player2 != null && player2.getConnected() && !player2.getSession().equals(session)) {
            return player2;
        }

        return null;
    }

    public Boolean needAPlayer() {
        return player1 == null || player2 == null;
    }

    public Boolean isRoomFull() {
        return player1 != null && player2 != null;
    }

    public Boolean isGameFinished() {
        return player1 == null && player2 == null ||
                player1 == null && !player2.getConnected() ||
                player2 == null && !player1.getConnected() ||
                !player1.getConnected() && !player2.getConnected();
    }

    private Boolean isPlayerConnected(WebSocketSession session) {
        if (player1 == null && player2 == null) return false;
        return Objects.requireNonNullElseGet(player1, () -> player2).getSession().equals(session);
    }

    public static String generateGameRoomId() {
        return UUID.randomUUID().toString();
    }

    @Nullable
    public Long getCatalogId() {
        return catalogId;
    }

    @Nullable
    public Player getPlayer1() {
        return player1;
    }

    @Nullable
    public Player getPlayer2() {
        return player2;
    }

    public @NotNull String getId() {
        return id;
    }
}



































