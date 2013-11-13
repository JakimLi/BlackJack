package intg;

import blackjack.controller.StartActionResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartActionIntegrationTest extends IntegrationTestBase {
    @Test
    public void should_response_two_cards_when_player_hit_start_and_error_if_game_already_started() throws Exception {

        StartActionResponse response = table.startGame();

        assertThat(response.isError(), is(false));
        assertThat(response.getCards().size(), is(2));
        assertThat(response.getCeteraScore(), greaterThan(0));
        assertThat(response.getHostScore(), greaterThan(0));

        response = table.startGame();
        assertThat(response.isError(), is(true));
        assertThat(response.getCards(), nullValue());
        assertThat(response.getCeteraScore(), is(0));
        assertThat(response.getHostScore(), is(0));
    }

    @Test
    public void should_restart_game_after_game_stopped() throws Exception {
        StartActionResponse response = table.startGame();

        assertThat(response.isError(), is(false));
        assertThat(response.getCards().size(), is(2));
        assertThat(response.getCeteraScore(), greaterThan(0));
        assertThat(response.getHostScore(), greaterThan(0));

        table.stopGame();

        table.initTable();
        StartActionResponse newResponse = table.startGame();

        assertThat(newResponse.isError(), is(false));
        assertThat(newResponse.getCards().size(), is(2));
        assertThat(newResponse.getCeteraScore(), greaterThan(0));
        assertThat(newResponse.getHostScore(), greaterThan(0));
    }
}
