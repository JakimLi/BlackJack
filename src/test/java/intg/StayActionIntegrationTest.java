package intg;

import blackjack.controller.StayActionResponse;
import blackjack.enums.GameState;
import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StayActionIntegrationTest extends IntegrationTestBase {
    @Test
    public void should_not_error_when_house_stay_in_normal_condition_and_return_a_winner() throws Exception {
        //given
        givenFiveCards(51, 0, 0, 0, 0);
        dealer.startGame();
        cetera.stay(dealer);

        //when
        StayActionResponse response = table.stay();

        //then
        assertThat(response.isError(), is(false));
        assertThat(response.getWinnerCode(), is("host"));
        assertThat(dealer.getGameState(), is(GameState.Ready));
    }

    @Test
    public void should_not_error_when_cetera_stay_in_normal_condition() throws Exception {
        //given
        dealer.startGame();

        //when
        StayActionResponse response = table.stay();

        //then
        assertThat(response.isError(), is(false));
        assertThat(response.getWinnerCode(), nullValue());
    }
}
