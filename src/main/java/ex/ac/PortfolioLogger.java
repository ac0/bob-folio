package ex.ac;

import ex.ac.wallet.WalletEntry;

import java.math.BigDecimal;

/**
 * Control portfolio logger interface; especially for tests
 */
public interface PortfolioLogger {
    void logEntry(WalletEntry walletEntry, BigDecimal entryValue);

    void logSummary(BigDecimal totalValue, String valueCode);

    void logErrorEntry(PortfolioEntryException entryError, boolean summarisedAbort);
}
