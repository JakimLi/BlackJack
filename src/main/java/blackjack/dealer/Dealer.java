package blackjack.dealer;

public class Dealer {

    private Card[] cards;

    public Card[] getCards() {
        cards = new Card[52];

        cards[0] = new Card("Ace", "Spades");
        cards[1] = new Card("Ace", "Hearts");
        cards[2] = new Card("Ace", "Clubs");
        cards[3] = new Card("Ace", "Diamonds");

        char value = '2';
        for (int i = 4; i < 36; i += 4) {
            cards[i] = new Card(value + "", "Spades");
            cards[i + 1] = new Card(value + "", "Hearts");
            cards[i + 2] = new Card(value + "", "Clubs");
            cards[i + 3] = new Card(value + "", "Diamonds");
            value++;
        }

        cards[36] = new Card("10", "Spades");
        cards[37] = new Card("10", "Hearts");
        cards[38] = new Card("10", "Clubs");
        cards[39] = new Card("10", "Diamonds");

        cards[40] = new Card("Jack", "Spades");
        cards[41] = new Card("Jack", "Hearts");
        cards[42] = new Card("Jack", "Clubs");
        cards[43] = new Card("Jack", "Diamonds");

        cards[44] = new Card("Queen", "Spades");
        cards[45] = new Card("Queen", "Hearts");
        cards[46] = new Card("Queen", "Clubs");
        cards[47] = new Card("Queen", "Diamonds");

        cards[48] = new Card("King", "Spades");
        cards[49] = new Card("King", "Hearts");
        cards[50] = new Card("King", "Clubs");
        cards[51] = new Card("King", "Diamonds");

        return cards;
    }
}
