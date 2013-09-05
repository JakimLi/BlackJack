package blackjack.player;

import blackjack.dealer.Card;
import blackjack.enums.Face;
import blackjack.exception.IllegalPlayerStateException;

import java.util.ArrayList;
import java.util.List;

import static blackjack.enums.Face.Up;

public class Player {
    private static final int UP_LIMIT_POINTS = 21;
    private List<Card> faceDownCards;
    private List<Card> faceUpCards;

    public Player() {
        faceDownCards = new ArrayList<Card>();
        faceUpCards = new ArrayList<Card>();
    }

    public void takeOneCard(Card card, Face face) {
        if (face == Up) {
            faceUpCards.add(card);
        } else {
            this.faceDownCards.add(card);
        }
    }

    public List<Card> getFaceUpCards() {
        return faceUpCards;
    }

    public int getValue() {
        return countCards(faceDownCards) + countCards(faceUpCards);
    }

    private int countCards(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getValue();
        }
        return sum;
    }

    public void hit() throws IllegalPlayerStateException {
        if (getValue() >= UP_LIMIT_POINTS) {
            throw new IllegalPlayerStateException("Point exceeds 21.");
        }
    }
}
