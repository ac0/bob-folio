package ex.ac.wallet;

/**
 * Represents the exception when an entry could not be parsed into a legal Wallet entry
 * Note - it's unchecked mainly owing to our reuse of an existing general interface (Iterator)
 */
public class InvalidWalletEntryException extends RuntimeException {
    public InvalidWalletEntryException(String message) {
        super(message);
    }
}
