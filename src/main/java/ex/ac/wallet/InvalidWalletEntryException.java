package ex.ac.wallet;

import ex.ac.PortfolioEntryException;

/**
 * Represents the exception when an entry could not be parsed into a legal Wallet entry
 */
public class InvalidWalletEntryException extends PortfolioEntryException {
    public InvalidWalletEntryException(String message) {
        super(message);
    }
}
