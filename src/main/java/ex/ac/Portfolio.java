package ex.ac;

import ex.ac.wallet.*;

import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Driver of the portfolio printing application.
 * <p>
 * Just performs very basic wiring of components and handles user's selection of input etc
 */
public class Portfolio {

    public static void main(String... args) {
        WalletReader walletReader = new WalletReaderImpl();
        Iterator<WalletEntry> walletEntries = walletReader.getEntries(new BufferedInputStream(System.in));

        printPortfolio(walletEntries);
    }

    private static void printPortfolio(Iterator<WalletEntry> walletEntries) {
        WalletProcessor walletProcessor = new WalletProcessorImpl();
        PortfolioLogger portfolioLogger = new ConsolePortfolioLogger();

        BigDecimal total = walletProcessor.valueOf(walletEntries, WalletEntry::getAmount, portfolioLogger);
        portfolioLogger.logSummary(total, "RAW");
    }
}
