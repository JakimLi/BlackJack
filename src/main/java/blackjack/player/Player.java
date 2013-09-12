package blackjack.player;

import blackjack.dealer.Card;
import blackjack.dealer.Dealer;
import blackjack.enums.Face;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;

import java.util.ArrayList;
import java.util.List;

import static blackjack.enums.Face.Down;
import static blackjack.enums.Face.Up;

public class Player {
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

    public void hit(Dealer dealer) throws IllegalPlayerStateException, IllegalGameStateException {
        dealer.dealt(this, dealer.pickACard(), Down);
    }

    public int cardAmount() {
        return faceDownCards.size() + faceUpCards.size();
    }
}
