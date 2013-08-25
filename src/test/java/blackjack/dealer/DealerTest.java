package blackjack.dealer;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        for (String value : values) {
            assertThat(cards, hasItemInArray(new Card(value, "Spades")));
            assertThat(cards, hasItemInArray(new Card(value, "Hearts")));
            assertThat(cards, hasItemInArray(new Card(value, "Clubs")));
            assertThat(cards, hasItemInArray(new Card(value, "Diamonds")));
        }
    }

    @Test
    public void should_pick_one_card_randomly_from_the_deck_of_card() throws Exception {
        //given
        Random randomGenerator = mock(Random.class);
        when(randomGenerator.nextInt(anyInt())).thenReturn(0);

        Dealer dealer = new Dealer();
        dealer.setRandomGenerator(randomGenerator);

        //when
        Card card = dealer.pickACard();

        //then
        assertThat(card, is(new Card("Ace", "Spades")));
    }

    @Test
    public void should_not_pick_card_already_picked() throws Exception {
        //given
        Dealer dealer = new Dealer();
        Random randomGenerator = mock(Random.class);
        when(randomGenerator.nextInt(anyInt())).thenReturn(0);
        dealer.setRandomGenerator(randomGenerator);

        //when
        Card firstCard = dealer.pickACard();
        Card secondCard = dealer.pickACard();
        //then

        assertThat(firstCard, is(new Card("Ace", "Spades")));
        assertThat(firstCard, not(secondCard));
    }

    @Test (expected = RuntimeException.class)
    public void should_error_after_all_cards_were_picked() throws Exception {
        //given
        Dealer dealer = new Dealer();
        Random randomGenerator = mock(Random.class);
        when(randomGenerator.nextInt(anyInt())).thenReturn(0);
        dealer.setRandomGenerator(randomGenerator);

        //when
        for (int i = 0; i < 52; i++) {
            dealer.pickACard();
        }

        dealer.pickACard();
    }
}
