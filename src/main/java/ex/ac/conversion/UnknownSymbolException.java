package ex.ac.conversion;

import ex.ac.PortfolioException;

/**
 * Either the symbol is invalid or an exchange does't know about it
 */
public class UnknownSymbolException extends PortfolioException {
    public UnknownSymbolException(String message) {
        super(message);
    }
}
