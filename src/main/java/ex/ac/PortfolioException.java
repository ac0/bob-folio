package ex.ac;

/**
 * Base exception for application
 *
 * Note - it's currently kept unchecked mainly owing to our reuse of an existing general interface (Iterator)
 */
public class PortfolioException extends RuntimeException {
    public PortfolioException(String message) {
        super(message);
    }
}
