package blackjack.dealer;

import org.junit.Test;

import static blackjack.enums.Suit.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CardTest {
    @Test
    public void should_know_cards_value() throws Exception {
        //given
        Card ace = new Card("Ace", Diamonds);
        Card two = new Card("2", Clubs);
        Card ten = new Card("10", Hearts);
        Card jack = new Card("Jack", Spades);
        Card queen = new Card("Queen", Spades);
        Card king = new Card("King", Spades);

        //then
        assertThat(ace.getValue(), is(1));
        assertThat(two.getValue(), is(2));
        assertThat(ten.getValue(), is(10));
        assertThat(jack.getValue(), is(10));
        assertThat(queen.getValue(), is(10));
        assertThat(king.getValue(), is(10));
    }
}
