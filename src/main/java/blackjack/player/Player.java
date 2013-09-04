package blackjack.player;

import blackjack.dealer.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> cards;

    public Player() {
        cards = new ArrayList<Card>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void takeOneCard(Card card) {
        this.cards.add(card);
    }
}
