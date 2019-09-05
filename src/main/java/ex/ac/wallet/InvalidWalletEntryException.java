package ex.ac.wallet;

import ex.ac.PortfolioException;

/**
 * Represents the exception when an entry could not be parsed into a legal Wallet entry
 */
public class InvalidWalletEntryException extends PortfolioException {
    public InvalidWalletEntryException(String message) {
        super(message);
    }
}
