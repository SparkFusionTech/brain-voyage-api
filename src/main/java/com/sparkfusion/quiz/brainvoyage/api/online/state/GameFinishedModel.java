package com.sparkfusion.quiz.brainvoyage.api.online.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameFinishedModel implements Serializable {

    private List<PlayerModel> players;

    public GameFinishedModel() {
    }

    public GameFinishedModel(List<PlayerModel> players) {
        this.players = players;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }

    public static GameFinishedModel initModel(GameRoom room) {
        Player player1 = room.getPlayer1();
        Player player2 = room.getPlayer2();

        PlayerModel playerModel1 = null;
        if (player1 != null) {
            playerModel1 = new PlayerModel(player1.getId(), player1.getName(), player1.getIconUrl(), player1.getScore());
        }

        PlayerModel playerModel2 = null;
        if (player2 != null) {
            playerModel2 = new PlayerModel(player2.getId(), player2.getName(), player2.getIconUrl(), player2.getScore());
        }

        List<PlayerModel> playerModels = new ArrayList<>(2);
        playerModels.add(playerModel1);
        playerModels.add(playerModel2);

        return new GameFinishedModel(playerModels);
    }

    public static class PlayerModel implements Serializable {

        private Long id;
        private String name;
        private String iconUrl;
        private Integer score;

        public PlayerModel() {
        }

        public PlayerModel(Long id, String name, String iconUrl, Integer score) {
            this.id = id;
            this.name = name;
            this.iconUrl = iconUrl;
            this.score = score;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
    }
}





























