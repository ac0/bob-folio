package ex.ac;

import ex.ac.wallet.*;

import java.io.BufferedInputStream;
import java.util.Iterator;

/**
 * Driver of the portfolio printing application.
 *
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

        walletProcessor.process(walletEntries, portfolioLogger);
    }
}
