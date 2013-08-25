package blackjack.dealer;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;

public class DealerTest {

    private String[] values = new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    @Test
    public void dealer_should_have_one_deck_of_cards() throws Exception {
        //given
        Dealer dealer = new Dealer();

        //then
        assertOneDeckOfCards(dealer.getCards());
    }

    private void assertOneDeckOfCards(Card[] cards) {

        assertThat(cards, arrayWithSize(52));

        for (String value: values){
            assertThat(cards, hasItemInArray(new Card(value, "Spades")));
            assertThat(cards, hasItemInArray(new Card(value, "Hearts")));
            assertThat(cards, hasItemInArray(new Card(value, "Clubs")));
            assertThat(cards, hasItemInArray(new Card(value, "Diamonds")));
        }
    }
}
