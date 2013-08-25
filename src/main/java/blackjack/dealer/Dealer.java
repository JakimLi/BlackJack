package blackjack.dealer;

import java.util.Random;

import static org.apache.commons.lang.ArrayUtils.removeElement;

public class Dealer {
    public static final String[] CARD_VALUES = new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    private Card[] cards = new Card[52];
    private Random randomGenerator;

    public Dealer() {
        getCards();
    }

    public Card[] getCards() {
        int i = 0;
        for (String value : CARD_VALUES) {
            i = fillCardsBySuits(i, value);
        }
        return cards;
    }

    private int fillCardsBySuits(int i, String value) {
        cards[i++] = new Card(value, "Spades");
        cards[i++] = new Card(value, "Hearts");
        cards[i++] = new Card(value, "Clubs");
        cards[i++] = new Card(value, "Diamonds");
        return i;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Card pickACard() {
        if (cards == null || cards.length <= 0) {
            throw new RuntimeException("No cards left in dealer's hands");
        }
        Card card = cards[randomGenerator.nextInt(cards.length)];
        cards = (Card[]) removeElement(cards, card);
        return card;
    }
}
