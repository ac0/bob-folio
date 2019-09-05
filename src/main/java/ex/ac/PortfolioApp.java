package ex.ac;

import ex.ac.conversion.ConvertingValueAssessor;
import ex.ac.conversion.CryptoCompareExchangeAdaptor;
import ex.ac.conversion.CurrencyExchange;
import ex.ac.conversion.ValueAssessor;
import ex.ac.wallet.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * PortfolioViewer implementation
 * and the driver of the portfolio printing application, performaing basic wiring of
 * components, deals with user input and any minimal interaction
 */
public class PortfolioApp implements PortfolioViewer {

    private static final String DEFAULT_INPUT_FILE_PATH = "./bobs_crypto.txt";

    // Potential 3rd party runtime dependency to the app
    private final CurrencyExchange currencyExchange;

    public PortfolioApp(CurrencyExchange currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public static void main(String... args) {
        // parse args and fix preferred source
        InputStream portfolioSourceInputStream = fixPortfolioSourceStream(args);

        new PortfolioApp(new CryptoCompareExchangeAdaptor()) // plug in crypto-compare
                .printPortfolio(portfolioSourceInputStream, new ConsolePortfolioLogger(System.out));// and print to stdout
    }

    @Override
    public void printPortfolio(InputStream portfolioSource, PortfolioLogger portfolioLogger) {
        WalletReader walletReader = new WalletReaderImpl();
        Iterator<WalletEntry> walletEntries = walletReader.getEntries(new BufferedInputStream(portfolioSource));

        WalletProcessor walletProcessor = new WalletProcessorImpl();

        ValueAssessor baseEuroValueAssessor = new ConvertingValueAssessor("EUR",
                currencyExchange);

        BigDecimal total = walletProcessor.valueOf(walletEntries, baseEuroValueAssessor, portfolioLogger);
        portfolioLogger.logSummary(total, "EUR");
    }

    /**
     * Figure out from the provided arg(s) or lack thereof, the expected inputstream, basically
     * // args[0] = <path-to-file> | - (stdin) | <not-provided> (./bobs_crypto.txt if exists, stdin if not)
     * ..and of course prints error/usage as necessary
     */
    private static InputStream fixPortfolioSourceStream(String[] args) {
        InputStream inputStream = System.in;
        String usageError = null, inputFilePath = DEFAULT_INPUT_FILE_PATH;
        if (args.length > 1) {
            usageError = "Unexpected parameter";
        } else {
            if (args.length > 0) {
                if (args[0].equals("-")) {
                    inputFilePath = null;
                } else {
                    inputFilePath = args[0];
                    inputStream = null;
                }
            }

            if (inputFilePath != null) {
                File inputFile = new File(inputFilePath);
                if (inputFile.exists() && !inputFile.isDirectory()) {
                    try {
                        inputStream = new FileInputStream(inputFile);
                    } catch (FileNotFoundException e) {// not unless someone deleted b/n the last 2 lines, anyway
                        throw new Error(e);
                    }
                }
            }

            if (inputStream == null) {
                usageError = "No legal file: " + inputFilePath;
            }

        }

        if (usageError != null) {
            System.err.println("Error! " + usageError);
            System.err.println("Usage: java -.. ex.ac.PortfolioApp [-|<path-to-file>]");
            System.exit(1);
        }

        // Writing to stderr here incase the user is redirecting to capture the result to a file
        System.err.println(inputStream == System.in ? "reading (stdin):"
                : "Using input file - " + inputFilePath);

        return inputStream;
    }

}
