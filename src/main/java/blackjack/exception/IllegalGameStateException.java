package blackjack.exception;

public class IllegalGameStateException extends Exception {
    public IllegalGameStateException(String msg) {
        super(msg);
    }
}
