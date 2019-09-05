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
        out.printf("%4d. %26s = %-16s%n", ++count, walletEntry, entryValue);
    }

    @Override
    public void logSummary(BigDecimal totalValue, String valueCode) {
        String valueCodeSuffix = valueCode == null ? "" : "(" + valueCode + ")";
        summarise(
                String.format("%32s = %-16s%n", "Total" + valueCodeSuffix, totalValue));
    }

    private void summarise(String summary) {
        out.println("-------------------------------------------------");
        out.println(summary);
    }

    @Override
    public void logErrorEntry(PortfolioEntryException entryError, boolean summarisedAbort) {
        out.printf("%4d.     ERROR!   %25s%n", ++count, entryError.getMessage());
        summarise(
                String.format("%31s%n", "Aborted"));
    }
}
