package ex.ac;

import ex.ac.conversion.ConvertingValueAssessor;
import ex.ac.conversion.CryptoCompareExchangeAdaptor;
import ex.ac.conversion.ValueAssessor;
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

        ValueAssessor baseEuroValueAssessor=new ConvertingValueAssessor("EUR",
                new CryptoCompareExchangeAdaptor());

        BigDecimal total = walletProcessor.valueOf(walletEntries, baseEuroValueAssessor, portfolioLogger);
        portfolioLogger.logSummary(total, "EUR");
    }
}
