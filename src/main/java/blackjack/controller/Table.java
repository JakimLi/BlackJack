package blackjack.controller;

import blackjack.dealer.Card;
import blackjack.dealer.Dealer;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;
import blackjack.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import static blackjack.dealer.Dealer.UP_LIMIT_POINTS;
import static blackjack.enums.PlayerState.Stay;

public class Table implements Serializable {
    private final static Logger logger = LoggerFactory.getLogger(Table.class);
    public static final String CETERA_USER_CODE = "cetera";
    public static final String HOUSE_USER_CODE = "host";
    public static final String EVEN_CODE = "even";
    public static Dealer dealer;
    public static Player house;
    public static Player cetera;

    static {
        initTable();
    }

    private static void initTable() {
        dealer = new Dealer();
        logger.info("Dealer initiated.");
        try {
            dealer.register(house);
            logger.info("House registered.");
            dealer.register(cetera);
            logger.info("Cetera registered.");
        } catch (IllegalGameStateException e) {
            logger.error(e.getMessage());
        }
    }

    public static StartActionResponse startGame() {
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
        } finally {
            return response;
        }
    }

    public static HitActionResponse hit() {
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
        } finally {
            return response;
        }
    }

    private static void checkGameStatusAndChooseWinner(HitActionResponse response, Player player) {
        if (player.getValue() == UP_LIMIT_POINTS) {
            response.setWinnerCode(player.equals(cetera) ? CETERA_USER_CODE : HOUSE_USER_CODE);
            restartGame();
        } else if (player.getValue() > UP_LIMIT_POINTS) {
            response.setWinnerCode(player.equals(cetera) ? HOUSE_USER_CODE : CETERA_USER_CODE);
            restartGame();
        }
        response.setGameStatus(dealer.getGameState());
    }

    private static void restartGame() {
        dealer.stopGame();
        initTable();
    }

    private static Player decidePlayer(HitActionResponse response) {
        Player player = cetera;
        response.setPlayerCode(CETERA_USER_CODE);
        if (cetera.getStatus() == Stay) {
            player = house;
            response.setPlayerCode(HOUSE_USER_CODE);
        }
        return player;
    }

    public static StayActionResponse stay() {
        Player player = cetera.getStatus() != Stay ? cetera : house;
        logger.info("Player:" + (player.equals(cetera) ? "cetera" : "house"));
        StayActionResponse response = new StayActionResponse();
        try {
            player.stay(dealer);
            response.setError(false);
            if (player.equals(house)) {
                logger.info("Winner:" + decideWinnerCode());
                logger.info("winnersValue:" + (decideWinnerCode().equals("cetera") ? cetera.getValue() : house. getValue()));
                response.setWinnerCode(decideWinnerCode());
                restartGame();
            }
        } catch (IllegalGameStateException e) {
            response.setError(true);
        }
        return response;
    }

    private static String decideWinnerCode() {
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
}