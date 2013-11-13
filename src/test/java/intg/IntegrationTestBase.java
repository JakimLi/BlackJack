package intg;

import blackjack.controller.Table;
import blackjack.dealer.Dealer;
import blackjack.player.Player;
import org.junit.After;
import org.junit.Before;

import java.util.Random;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTestBase {
    protected Table table;
    protected Dealer dealer;
    protected Player cetera;
    protected Player house;

    protected void givenFiveCards(int index1, int index2, int index3, int index4, int index5) {
        Random random = mock(Random.class);
        when(random.nextInt(anyInt())).thenReturn(index1, index2, index3, index4, index5);
        dealer.setRandomGenerator(random);
    }

//    @Before
//    public void setUp() throws Exception {
//        dealer = new Dealer();
//        house = new Player();
//        cetera = new Player();
//        dealer.register(house);
//        dealer.register(cetera);
//    }

    @After
    public void tearDown() throws Exception {
        dealer.stopGame();
    }

    @Before
    public void setUp() throws Exception {
        table = new Table();
        dealer = new Dealer();
        house = new Player();
        cetera = new Player();
        table.setDealer(dealer);
        table.setHouse(house);
        table.setCetera(cetera);
        table.initTable();
    }
}
