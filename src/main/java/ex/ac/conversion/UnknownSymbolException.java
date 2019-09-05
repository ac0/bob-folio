package ex.ac.conversion;

import ex.ac.PortfolioEntryException;

/**
 * Either the symbol is invalid or an exchange does't know about it
 */
public class UnknownSymbolException extends PortfolioEntryException {
    public UnknownSymbolException(String message) {
        super(message);
    }
}
