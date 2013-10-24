package blackjack.player;

import blackjack.dealer.Card;
import blackjack.dealer.Dealer;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;
import org.junit.Test;

import java.util.Random;

import static blackjack.enums.Suit.Diamonds;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {
    @Test
    public void should_not_hit_if_the_points_of_player_is_larger_than_21() throws Exception {
        //given
        Dealer dealer = new Dealer();
        Random random = mock(Random.class);
        when(random.nextInt(anyInt()))
                .thenReturn(0)
                .thenReturn(50)
                .thenReturn(49)
                .thenReturn(48);
        dealer.setRandomGenerator(random);


        Player player = new Player();

        dealer.register(player);
        dealer.register(new Player());
        dealer.startGame();
        player.takeOneCard(new Card("Jack", Diamonds));

        //when && then
        assertCannotHit(player, dealer);

        player.takeOneCard(new Card("8", Diamonds));

        assertCannotHit(player, dealer);
    }

    @Test
    public void should_get_one_card_when_successfully_hit() throws Exception {
        //given
        Player player = new Player();
        Dealer dealer = new Dealer();
        dealer.register(new Player());
        dealer.register(player);
        dealer.startGame();

        //when
        player.hit(dealer, null);

        //then
        assertThat(player.cardAmount(), is(3));
    }

    @Test
    public void should_not_hit_if_game_is_not_started() throws Exception {
        //given
        Player player = new Player();
        Dealer dealer = new Dealer();

        //when
        assertCannotHit(player, dealer);
    }

    @Test
    public void player_cannot_hit_after_stay() throws Exception {
        //given
        Player player = new Player();
        Dealer dealer = new Dealer();
        dealer.register(player);
        dealer.register(new Player());
        dealer.startGame();
        int valueInHand = player.getValue();

        //when
        player.stay(dealer);

        //then
        assertThat(player.getValue(), is(valueInHand));
        try {
            player.hit(dealer, null);
            fail();
        } catch (IllegalPlayerStateException ex) {
            assertTrue(true);
        }
    }

    @Test(expected = IllegalGameStateException.class)
    public void player_cannot_stay_if_game_is_not_started() throws Exception {
        //given
        Dealer dealer = new Dealer();
        Player player = new Player();
        dealer.register(new Player());
        dealer.register(player);

        //when
        player.stay(dealer);
    }

    private void assertCannotHit(Player player, Dealer dealer) throws IllegalGameStateException {
        try {
            player.hit(dealer, null);
            fail();
        } catch (IllegalPlayerStateException ex) {
            assertTrue(true);
            return;
        } catch (IllegalGameStateException ex) {
            assertTrue(true);
            return;
        }
        fail();
    }
}
