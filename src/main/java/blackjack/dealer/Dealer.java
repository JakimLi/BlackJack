package blackjack.dealer;

import blackjack.enums.GameState;
import blackjack.enums.PlayerState;
import blackjack.exception.IllegalGameStateException;
import blackjack.exception.IllegalPlayerStateException;
import blackjack.player.Player;

import java.util.Random;

import static blackjack.enums.GameState.Ongoing;
import static blackjack.enums.GameState.Ready;
import static blackjack.enums.Suit.*;
import static org.apache.commons.lang.ArrayUtils.removeElement;

public class Dealer {
    public static final String[] CARD_VALUES = new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    public static final int UP_LIMIT_POINTS = 21;

    private Card[] cards;
    private Random randomGenerator;
    private Player house;
    private Player cetera;
    private GameState state;

    public Dealer() {
        state = Ready;
        randomGenerator = new Random();
    }

    public Card[] shuffle() {
        cards = new Card[52];
        int i = 0;
        for (String value : CARD_VALUES) {
            i = fillCardsBySuits(i, value);
        }
        return cards;
    }

    private int fillCardsBySuits(int i, String value) {
        cards[i++] = new Card(value, Spades);
        cards[i++] = new Card(value, Hearts);
        cards[i++] = new Card(value, Clubs);
        cards[i++] = new Card(value, Diamonds);
        return i;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public Card pickACard() {
        if (cards == null || cards.length <= 0) {
            throw new RuntimeException("No cards left in dealer's hands");
        }
        Card card = cards[randomGenerator.nextInt(cards.length)];
        cards = (Card[]) removeElement(cards, card);
        return card;
    }

    public void register(Player player) throws IllegalGameStateException {
        if (house == null) {
            house = player;
        } else if (cetera == null) {
            cetera = player;
        } else {
            throw new IllegalGameStateException("Only two players in one game.");
        }
    }

    public Player getHouse() {
        return house;
    }

    public Player getCetera() {
        return cetera;
    }

    public void startGame() throws IllegalGameStateException, IllegalPlayerStateException {
        if (isTwoPlayersRegistered()) {
            throw new IllegalGameStateException("Short of players.");
        }
        if (isGameAlreadyStarted()) {
            throw new IllegalGameStateException("Game already started.");
        }
        state = Ongoing;
        dealTwoCards(house);
        dealTwoCards(cetera);
    }

    private void dealTwoCards(Player player) throws IllegalGameStateException, IllegalPlayerStateException {
        dealt(player, pickACard());
        dealt(player, pickACard());
    }

    private boolean isGameAlreadyStarted() {
        return state == Ongoing;
    }

    private boolean isTwoPlayersRegistered() {
        return house == null || cetera == null;
    }

    public GameState getGameState() {
        return state;
    }

    public int count(Player player) {
        return player.getValue();
    }

    public void dealt(Player player, Card card) throws IllegalGameStateException, IllegalPlayerStateException {
        if (canDeal(player)) {
            player.takeOneCard(card);
        }
    }

    public boolean canDeal(Player player) throws IllegalGameStateException, IllegalPlayerStateException {
        if (!isGameAlreadyStarted()) {
            throw new IllegalGameStateException("Game is not started, cannot dealt cards to anyone.");
        }
        if (count(player) >= UP_LIMIT_POINTS) {
            throw new IllegalPlayerStateException("Point exceeds 21.");
        }
        if (player.getStatus() == PlayerState.Stay) {
            throw new IllegalPlayerStateException("Player is staying.");
        }
        return true;
    }

    public void stopGame() {
        house = null;
        cetera = null;
        state = GameState.Ready;
    }
}
