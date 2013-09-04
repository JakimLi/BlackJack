package blackjack.dealer;

import blackjack.enums.GameState;
import blackjack.exception.IllegalGameStateException;
import blackjack.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static blackjack.dealer.Dealer.CARD_VALUES;
import static blackjack.enums.Suit.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DealerTest {

    private Dealer dealer;

    @Before
    public void setUp() throws Exception {
        dealer = new Dealer();
        Random randomGenerator = mock(Random.class);
        when(randomGenerator.nextInt(anyInt())).thenReturn(0);
        dealer.setRandomGenerator(randomGenerator);
    }

    @Test
    public void dealer_should_have_one_deck_of_cards() throws Exception {
        assertOneDeckOfCards(dealer.getCards());
    }

    private void assertOneDeckOfCards(Card[] cards) {

        assertThat(cards, arrayWithSize(52));

        for (String value : CARD_VALUES) {
            assertThat(cards, hasItemInArray(new Card(value, Spades)));
            assertThat(cards, hasItemInArray(new Card(value, Hearts)));
            assertThat(cards, hasItemInArray(new Card(value, Clubs)));
            assertThat(cards, hasItemInArray(new Card(value, Diamonds)));
        }
    }

    @Test
    public void should_pick_one_card_randomly_from_the_deck_of_card() throws Exception {
        //when
        Card card = dealer.pickACard();

        //then
        assertThat(card, is(new Card("Ace", Spades)));
    }

    @Test
    public void should_not_pick_card_already_picked() throws Exception {
        //when
        Card firstCard = dealer.pickACard();
        Card secondCard = dealer.pickACard();

        //then
        assertThat(firstCard, is(new Card("Ace", Spades)));
        assertThat(firstCard, not(secondCard));
    }

    @Test(expected = RuntimeException.class)
    public void should_error_after_all_cards_were_picked() throws Exception {
        //when
        for (int i = 0; i < 52; i++) {
            dealer.pickACard();
        }

        dealer.pickACard();
    }

    @Test
    public void should_only_register_two_players() throws Exception {
        //given
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        dealer.register(player1);
        dealer.register(player2);

        //when && then
        try {
            dealer.register(player3);
            fail();
        } catch (IllegalGameStateException ex) {
            assertTrue(true);
        }
        assertThat(dealer.getHouse(), is(player1));
        assertThat(dealer.getCetera(), is(player2));
    }

    @Test(expected = IllegalGameStateException.class)
    public void should_not_start_the_game_if_player_count_less_than_2() throws Exception {
        dealer.startGame();
    }

    @Test
    public void should_not_start_the_game_if_game_already_started() throws Exception {
        dealer.register(new Player());
        dealer.register(new Player());
        dealer.startGame();

        try {
            dealer.startGame();
            fail("Expect exception" + IllegalGameStateException.class.toString()
                    + ", actually got nothing");
        } catch (IllegalGameStateException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void should_start_game_if_player_count_is_2_and_game_is_ready() throws Exception {
        //given
        dealer.register(new Player());
        dealer.register(new Player());
        assertThat(dealer.getGameState(), is(GameState.Ready));

        //when
        dealer.startGame();

        //then
        assertThat(dealer.getGameState(), is(GameState.Ongoing));
    }

    @Test
    public void should_count_points_of_player_cards() throws Exception {
        //given
        Player player = new Player();
        player.takeOneCard(new Card("Ace", Diamonds));
        player.takeOneCard(new Card("10", Diamonds));
        player.takeOneCard(new Card("Jack", Diamonds));
        player.takeOneCard(new Card("Queen", Diamonds));
        player.takeOneCard(new Card("King", Diamonds));

        //when
        int value = dealer.count(player.getCards());

        //then
        assertThat(value, is(41));
    }

    @Test(expected = IllegalGameStateException.class)
    public void should_not_dealt_card_if_game_is_not_started() throws Exception {
        //given
        Player player = new Player();

        //when
        dealer.dealt(player, new Card("Ace", Diamonds));
    }
}
