package blackjack.dealer;

public class Dealer {

    private String[] values = new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private Card[] cards = new Card[52];

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
}
