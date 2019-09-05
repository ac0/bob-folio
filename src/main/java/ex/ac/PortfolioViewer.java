package ex.ac;

import java.io.InputStream;

/**
 * Defines the overall interface of the application, where portfolios are read through a low
 * level input stream, parsed, processed and output to a higher level logger, potentially stream
 */
public interface PortfolioViewer {
    boolean printPortfolio(InputStream portfolioSource, PortfolioLogger portfolioLogger);
}
