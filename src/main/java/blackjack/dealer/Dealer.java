package blackjack.dealer;

import java.util.Random;

import static org.apache.commons.lang.ArrayUtils.removeElement;

public class Dealer {
    private final String[] values = new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    private Card[] cards = new Card[52];
    private Random randomGenerator;

    public Dealer() {
        getCards();
    }

    public Card[] getCards() {
        int i = 0;
        for (String value : values) {
            cards[i++] = new Card(value, "Spades");
            cards[i++] = new Card(value, "Hearts");
            cards[i++] = new Card(value, "Clubs");
            cards[i++] = new Card(value, "Diamonds");
        }
        return cards;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Card pickACard() {
        if( cards == null || cards.length <= 0) {
            throw new RuntimeException("No cards left in dealer's hands");
        }
        int random = randomGenerator.nextInt(cards.length);
        Card card = cards[random];
        cards = (Card[]) removeElement(cards, card);
        return card;
    }
}
