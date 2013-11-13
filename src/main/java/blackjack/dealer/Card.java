package blackjack.dealer;

import blackjack.enums.Suit;
import org.apache.commons.lang.ArrayUtils;

public class Card {

    public static final int THIRTY_ONE = 31;
    private final String value;
    private final Suit suit;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card) o;

        return suit.equals(card.suit) && value.equals(card.value);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = THIRTY_ONE * result + suit.hashCode();
        return result;
    }

    public Card(String value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getNumberValue() {
        if (isFaceCard()) {
            return 10;
        } else {
            return ArrayUtils.indexOf(Dealer.CARD_VALUES, value) + 1;
        }
    }

    public String getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    private boolean isFaceCard() {
        return value.equals("Jack") || value.equals("Queen") || value.equals("King");
    }
}
