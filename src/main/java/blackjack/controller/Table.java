package blackjack.controller;

import blackjack.dealer.Card;
import blackjack.dealer.Dealer;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;
import blackjack.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;

import static blackjack.dealer.Dealer.UP_LIMIT_POINTS;
import static blackjack.enums.PlayerState.Stay;

public class Table implements Serializable {
    private final static Logger LOGGER = LoggerFactory.getLogger(Table.class);
    private static final String CETERA_USER_CODE = "cetera";
    private static final String HOUSE_USER_CODE = "host";
    private static final String EVEN_CODE = "even";
    private Dealer dealer;
    private Player house;
    private Player cetera;

    public void initTable() {
        try {
            dealer.shuffle();
            house.getReady();
            cetera.getReady();
            dealer.register(house);
            dealer.register(cetera);
        } catch (IllegalGameStateException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public StartActionResponse startGame() {
        StartActionResponse response = new StartActionResponse();
        try {
            dealer.startGame();
            response.setError(false);
            response.setCards(cetera.getCards());
            response.setCeteraScore(cetera.getValue());
            response.setHostScore(house.getValue());
        } catch (IllegalGameStateException e) {
            response.setError(true);
        } catch (IllegalPlayerStateException e) {
            response.setError(true);
        }
        return response;
    }

    public HitActionResponse hit() {
        Card card = dealer.pickACard();
        HitActionResponse response = new HitActionResponse();
        try {
            Player player = decidePlayer(response);
            player.hit(dealer, card);
            response.setError(false);
            checkGameStatusAndChooseWinner(response, player);
            response.setCard(player.getCards().get(player.getCards().size() - 1));
            response.setValue(player.getValue());
        } catch (IllegalPlayerStateException e) {
            response.setError(true);
        } catch (IllegalGameStateException e) {
            response.setError(true);
        }
        return response;
    }

    private void checkGameStatusAndChooseWinner(HitActionResponse response, Player player) {
        if (player.getValue() == UP_LIMIT_POINTS) {
            response.setWinnerCode(player.equals(cetera) ? CETERA_USER_CODE : HOUSE_USER_CODE);
            stopGame();
        } else if (player.getValue() > UP_LIMIT_POINTS) {
            response.setWinnerCode(player.equals(cetera) ? HOUSE_USER_CODE : CETERA_USER_CODE);
            stopGame();
        }
        response.setGameStatus(dealer.getGameState());
    }

    public void stopGame() {
        dealer.stopGame();
    }

    private Player decidePlayer(HitActionResponse response) {
        Player player = cetera;
        response.setPlayerCode(CETERA_USER_CODE);
        if (cetera.getStatus() == Stay) {
            player = house;
            response.setPlayerCode(HOUSE_USER_CODE);
        }
        return player;
    }

    public StayActionResponse stay() {
        Player player = cetera.getStatus() != Stay ? cetera : house;
        LOGGER.info("Player:" + (player.equals(cetera) ? "cetera" : "house"));
        StayActionResponse response = new StayActionResponse();
        try {
            player.stay(dealer);
            response.setError(false);
            if (player.equals(house)) {
                LOGGER.info("Winner:" + decideWinnerCode());
                response.setWinnerCode(decideWinnerCode());
                stopGame();
            }
        } catch (IllegalGameStateException e) {
            response.setError(true);
        }
        return response;
    }

    private String decideWinnerCode() {
        int ceteraValue = cetera.getValue();
        int houseValue = house.getValue();
        if (ceteraValue > houseValue) {
            return CETERA_USER_CODE;
        } else if (ceteraValue < houseValue) {
            return HOUSE_USER_CODE;
        } else {
            return EVEN_CODE;
        }
    }

    @Required
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    @Required
    public void setHouse(Player house) {
        this.house = house;
    }

    @Required
    public void setCetera(Player cetera) {
        this.cetera = cetera;
    }

    public Dealer getDealer() {
        return dealer;
    }

}