package blackjack.dealer;

public class Card {

    private final String value;
    private final String suit;

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

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }
}
