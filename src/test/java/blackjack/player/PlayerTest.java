package blackjack.player;

import blackjack.dealer.Card;
import blackjack.dealer.Dealer;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;
import org.junit.Test;

import static blackjack.enums.Face.Up;
import static blackjack.enums.Suit.Diamonds;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PlayerTest {
    @Test
    public void should_not_hit_if_the_points_of_player_is_not_less_than_21() throws Exception {
        //given
        Player player = new Player();
        player.takeOneCard(new Card("Ace", Diamonds), Up);
        player.takeOneCard(new Card("10", Diamonds), Up);
        player.takeOneCard(new Card("Jack", Diamonds), Up);

        Dealer dealer = new Dealer();
        dealer.register(player);
        dealer.register(new Player());
        dealer.startGame();

        //when && then
        assertCannotHit(player, dealer);

        player.takeOneCard(new Card("8", Diamonds), Up);

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
        player.hit(dealer);

        //then
        assertThat(player.cardAmount(), is(1));
    }

    @Test
    public void should_not_hit_if_game_is_not_started() throws Exception {
        //given
        Player player = new Player();
        Dealer dealer = new Dealer();

        //when
        assertCannotHit(player, dealer);
    }

    private void assertCannotHit(Player player, Dealer dealer) throws IllegalGameStateException {
        try {
            player.hit(dealer);
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
