package blackjack.controller;

import blackjack.dealer.Card;

import java.util.List;

public class StartActionResponse {
    private boolean error;
    private List<Card> cards;
    private int ceteraScore;
    private int hostScore;

    public boolean isError() {
        return error;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getCeteraScore() {
        return ceteraScore;
    }

    public void setCeteraScore(int ceteraScore) {
        this.ceteraScore = ceteraScore;
    }

    public int getHostScore() {
        return hostScore;
    }

    public void setHostScore(int hostScore) {
        this.hostScore = hostScore;
    }
}
