package intg;

import blackjack.controller.HitActionResponse;
import blackjack.dealer.Card;
import blackjack.enums.GameState;
import org.junit.Test;

import static blackjack.enums.Suit.Hearts;
import static blackjack.enums.Suit.Spades;
import static blackjack.enums.Suit.Clubs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class HitActionIntegrationTest extends IntegrationTestBase {

    @Test
    public void should_get_one_card_one_user_and_one_value_after_hit() throws Exception {
        //given
        givenFiveCards(51, 50, 0, 0, 0);
        dealer.startGame();

        //when
        HitActionResponse response = table.hit();

        //then
        assertUnFinishedGameResponse(response, false, new Card("Ace", Clubs), "cetera", 3);
    }

    @Test
    public void should_get_one_card_one_user_and_one_value_and_stop_game_when_cetera_score_is_21() throws Exception {
        //given
        givenFiveCards(51, 50, 49, 48, 0);
        dealer.startGame();

        //when
        HitActionResponse response = table.hit();

        //then
        assertFinishedGameResponse(response, false, new Card("Ace", Spades), "cetera", 21, "cetera");
    }

    @Test
    public void should_get_one_card_user_cetera_and_total_value_and_game_stopped_house_win() throws Exception {
        //given
        givenFiveCards(51, 50, 49, 48, 6);
        dealer.startGame();

        //when
        HitActionResponse response = table.hit();

        //then
        assertFinishedGameResponse(response, false, new Card("2", Clubs), "cetera", 22, "host");
    }

    @Test
    public void should_switch_player_after_cetera_stay() throws Exception {
        //given
        givenFiveCards(51, 0, 49, 0, 0);
        dealer.startGame();
        cetera.stay(dealer);

        //when
        HitActionResponse response = table.hit();

        //then
        assertUnFinishedGameResponse(response, false, new Card("Ace", Clubs), "host", 12);
    }

    @Test
    public void should_switch_user_and_then_finished_game() throws Exception {
        //given
        givenFiveCards(51, 50, 49, 0, 0);
        dealer.startGame();
        cetera.stay(dealer);

        //when
        HitActionResponse response = table.hit();

        //then
        assertFinishedGameResponse(response, false, new Card("Ace", Hearts), "host", 21, "host");
    }

    @Test
    public void should_switch_user_and_then_finished_game_and_cetera_win() throws Exception {
        //given
        givenFiveCards(51, 50, 49, 0, 3);
        dealer.startGame();
        cetera.stay(dealer);

        //when
        HitActionResponse response = table.hit();

        //then
        assertFinishedGameResponse(response, false, new Card("2", Spades), "host", 22, "cetera");
    }

    private void assertFinishedGameResponse(
            HitActionResponse response, boolean isError, Card lastCard, String endByWho,
            int endValue, String winner) {
        assertThat(response.isError(), is(isError));
        assertThat(response.getCard(), is(lastCard));
        assertThat(response.getPlayerCode(), is(endByWho));
        assertThat(response.getValue(), is(endValue));
        assertThat(response.getWinnerCode(), is(winner));
        assertThat(response.getGameStatus(), is(GameState.Ready));
    }

    private void assertUnFinishedGameResponse(
            HitActionResponse response, boolean isError, Card lastCard, String lastHitter,
            int endValue) {
        assertThat(response.isError(), is(isError));
        assertThat(response.getCard(), is(lastCard));
        assertThat(response.getPlayerCode(), is(lastHitter));
        assertThat(response.getValue(), is(endValue));
        assertThat(response.getWinnerCode(), nullValue());
        assertThat(response.getGameStatus(), is(GameState.Ongoing));
    }
}
