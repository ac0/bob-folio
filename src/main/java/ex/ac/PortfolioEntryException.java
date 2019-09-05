package ex.ac;

/**
 * Base exception for application
 *
 * Note - it's currently kept unchecked mainly owing to our reuse of an existing general interface (Iterator)
 */
public class PortfolioEntryException extends RuntimeException {
    public PortfolioEntryException(String message) {
        super(message);
    }
}
