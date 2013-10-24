package blackjack.player;

import blackjack.dealer.Card;
import blackjack.dealer.Dealer;
import blackjack.enums.PlayerState;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;

import java.util.ArrayList;
import java.util.List;

import static blackjack.enums.GameState.Ongoing;
import static blackjack.enums.PlayerState.Active;
import static blackjack.enums.PlayerState.Stay;

public class Player {
    private PlayerState status;
    private List<Card> cardsInHand;

    public Player() {
        cardsInHand = new ArrayList<Card>();
        status = Active;
    }

    public void takeOneCard(Card card) {
        cardsInHand.add(card);
    }

    public int getValue() {
        return countCards(cardsInHand);
    }

    private int countCards(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getNumberValue();
        }
        return sum;
    }

    public void hit(Dealer dealer, Card card) throws IllegalPlayerStateException, IllegalGameStateException {
        if (dealer.canDeal(this)) {
            dealer.dealt(this, card);
        }
    }

    public int cardAmount() {
        return cardsInHand.size();
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

    public List<Card> getCards() {
        return cardsInHand;
    }
}
