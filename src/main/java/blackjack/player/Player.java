package blackjack.player;

import blackjack.dealer.Card;
import blackjack.dealer.Dealer;
import blackjack.enums.Face;
import blackjack.enums.PlayerState;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;

import java.util.ArrayList;
import java.util.List;

import static blackjack.enums.Face.Down;
import static blackjack.enums.Face.Up;
import static blackjack.enums.GameState.Ongoing;
import static blackjack.enums.PlayerState.Active;
import static blackjack.enums.PlayerState.Stay;

public class Player {
    private List<Card> faceDownCards;
    private List<Card> faceUpCards;
    private PlayerState status;

    public Player() {
        faceDownCards = new ArrayList<Card>();
        faceUpCards = new ArrayList<Card>();
        status = Active;
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
        if (dealer.canDeal(this)) {
            dealer.dealt(this, dealer.pickACard(), Down);
        }
    }

    public int cardAmount() {
        return faceDownCards.size() + faceUpCards.size();
    }

    public void stay(Dealer dealer) throws IllegalGameStateException {
        if (dealer.getGameState() != Ongoing) {
            throw new IllegalGameStateException("Game is not started");
        }
        status = Stay;
    }

    public PlayerState getStatus() {
        return status;
    }
}
