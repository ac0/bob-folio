package ex.ac;

import ex.ac.wallet.WalletEntry;

import java.io.PrintStream;
import java.math.BigDecimal;

/**
 * Console output implementation. Sink, hence left out of test net. Not much to gain forced mocking
 * of out.printf and limit class from using say another method
 */
public class ConsolePortfolioLogger implements PortfolioLogger {

    private int count;

    private final PrintStream out;

    public ConsolePortfolioLogger(PrintStream out) {
        this.out = out;
    }

    @Override
    public void logEntry(WalletEntry walletEntry, BigDecimal entryValue) {
        out.printf("%4d. %20s = %-10s%n", ++count, walletEntry, entryValue);
    }

    @Override
    public void logSummary(BigDecimal totalValue, String valueCode) {
        String valueCodeSuffix = valueCode == null ? "" : "(" + valueCode + ")";
        out.println("-----------------------------------");
        out.printf("%26s = %-10s%n%n", "Total" + valueCodeSuffix, totalValue);
    }
}
