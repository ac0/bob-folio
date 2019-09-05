package ex.ac;

import ex.ac.wallet.WalletEntry;

import java.math.BigDecimal;

/**
 * Console output implementation. Sink, hence left out of test net. Not much to gain forced mocking
 * of out.printf and limit class from using say another method
 */
public class ConsolePortfolioLogger implements PortfolioLogger {

    private int count;

    @Override
    public void logEntry(WalletEntry walletEntry, BigDecimal entryValue) {
        System.out.printf("%4d. %20s = %-10s%n", ++count, walletEntry, entryValue);
    }

    @Override
    public void logSummary(BigDecimal totalValue, String valueCode) {
        String valueCodeSuffix = valueCode == null ? "" : "(" + valueCode + ")";
        System.out.println("-----------------------------------");
        System.out.printf("%26s = %-10s%n%n", "Total" + valueCodeSuffix, totalValue);
    }
}
