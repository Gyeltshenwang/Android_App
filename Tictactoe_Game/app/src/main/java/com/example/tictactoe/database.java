package com.example.tictactoe;

import android.widget.TextView;

public class database {
    private int playerOneScore;
    private int playerTwoScore;
    private String Status;

    public database() {
    }

    public database(int playerOneScore, int playerTwoScore, String status) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
        Status = status;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
