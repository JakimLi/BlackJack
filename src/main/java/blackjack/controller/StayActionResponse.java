package blackjack.controller;

public class StayActionResponse {
    private boolean error;
    private String winnerCode;

    public boolean isError() {
        return error;
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setWinnerCode(String winnerCode) {
        this.winnerCode = winnerCode;
    }
}
