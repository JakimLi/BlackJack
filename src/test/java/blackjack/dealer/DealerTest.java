package blackjack.dealer;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;

public class DealerTest {
    @Test
    public void dealer_should_have_one_deck_of_cards() throws Exception {
        //given
        Dealer dealer = new Dealer();

        //then
        assertOneDeckOfCards(dealer.getCards());
    }

    private void assertOneDeckOfCards(Card[] cards) {

        assertThat(cards, arrayWithSize(52));

        assertThat(cards, hasItemInArray(new Card("Ace", "Spades")));
        assertThat(cards, hasItemInArray(new Card("Ace", "Hearts")));
        assertThat(cards, hasItemInArray(new Card("Ace", "Clubs")));
        assertThat(cards, hasItemInArray(new Card("Ace", "Diamonds")));

        for (char i = '2'; i < '9'; i++) {
            assertThat(cards, hasItemInArray(new Card(i + "", "Spades")));
            assertThat(cards, hasItemInArray(new Card(i + "", "Hearts")));
            assertThat(cards, hasItemInArray(new Card(i + "", "Clubs")));
            assertThat(cards, hasItemInArray(new Card(i + "", "Diamonds")));
        }

        assertThat(cards, hasItemInArray(new Card("10", "Spades")));
        assertThat(cards, hasItemInArray(new Card("10", "Hearts")));
        assertThat(cards, hasItemInArray(new Card("10", "Clubs")));
        assertThat(cards, hasItemInArray(new Card("10", "Diamonds")));

        assertThat(cards, hasItemInArray(new Card("Jack", "Spades")));
        assertThat(cards, hasItemInArray(new Card("Jack", "Hearts")));
        assertThat(cards, hasItemInArray(new Card("Jack", "Clubs")));
        assertThat(cards, hasItemInArray(new Card("Jack", "Diamonds")));

        assertThat(cards, hasItemInArray(new Card("Queen", "Spades")));
        assertThat(cards, hasItemInArray(new Card("Queen", "Hearts")));
        assertThat(cards, hasItemInArray(new Card("Queen", "Clubs")));
        assertThat(cards, hasItemInArray(new Card("Queen", "Diamonds")));

        assertThat(cards, hasItemInArray(new Card("King", "Spades")));
        assertThat(cards, hasItemInArray(new Card("King", "Hearts")));
        assertThat(cards, hasItemInArray(new Card("King", "Clubs")));
        assertThat(cards, hasItemInArray(new Card("King", "Diamonds")));
    }
}
