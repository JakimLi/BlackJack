package blackjack.dealer;

import blackjack.enums.Suit;
import org.apache.commons.lang.ArrayUtils;

public class Card {

    private final String value;
    private final Suit suit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return suit.equals(card.suit) && value.equals(card.value);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    public Card(String value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        if (isFaceCard()) {
            return 10;
        } else {
            return ArrayUtils.indexOf(Dealer.CARD_VALUES, value) + 1;
        }
    }

    private boolean isFaceCard() {
        return value.equals("Jack") || value.equals("Queen") || value.equals("King");
    }
}
