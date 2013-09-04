package blackjack.dealer;

import blackjack.enums.GameState;
import blackjack.exception.IllegalGameStateException;
import blackjack.player.Player;

import java.util.List;
import java.util.Random;

import static blackjack.enums.GameState.Ongoing;
import static blackjack.enums.GameState.Ready;
import static blackjack.enums.Suit.*;
import static org.apache.commons.lang.ArrayUtils.removeElement;

public class Dealer {
    public static final String[] CARD_VALUES = new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    private Card[] cards = new Card[52];
    private Random randomGenerator;
    private Player house;
    private Player cetera;
    private GameState state;

    public Dealer() {
        state = Ready;
        getCards();
    }

    public Card[] getCards() {
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

    public void startGame() throws IllegalGameStateException {
        if (isTwoPlayersRegistered()) {
            throw new IllegalGameStateException("Short of players.");
        }
        if (isGameAlreadyStarted()) {
            throw new IllegalGameStateException("Game already started.");
        }
        state = Ongoing;
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

    public int count(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getValue();
        }
        return sum;
    }
}
