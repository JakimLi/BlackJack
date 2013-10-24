package blackjack.controller;

import blackjack.dealer.Card;
import blackjack.enums.GameState;

public class HitActionResponse {
    private boolean error;
    private String playerCode;
    private Card card;
    private int value;
    private GameState gameStatus;
    private String winnerCode;

    public boolean isError() {
        return error;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public Card getCard() {
        return card;
    }

    public int getValue() {
        return value;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public GameState getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameState gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void setWinnerCode(String winnerCode) {
        this.winnerCode = winnerCode;
    }
}
