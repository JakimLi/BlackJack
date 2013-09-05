package blackjack.player;

import blackjack.dealer.Card;
import blackjack.exception.IllegalPlayerStateException;
import org.junit.Test;

import static blackjack.enums.Face.Up;
import static blackjack.enums.Suit.Diamonds;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;

public class PlayerTest {
    @Test
    public void should_not_hit_if_the_points_of_player_is_not_less_than_21() throws Exception {
        //given
        Player player = new Player();
        player.takeOneCard(new Card("Ace", Diamonds), Up);
        player.takeOneCard(new Card("10", Diamonds), Up);
        player.takeOneCard(new Card("Jack", Diamonds), Up);

        //when && then
        assertCannotHit(player);

        player.takeOneCard(new Card("8", Diamonds), Up);

        assertCannotHit(player);
    }

    private void assertCannotHit(Player player) {
        try {
            player.hit();
            fail();
        } catch (IllegalPlayerStateException ex) {
            assertTrue(true);
        }
    }
}
