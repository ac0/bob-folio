package ex.ac;

import ex.ac.wallet.WalletEntry;

import java.math.BigDecimal;

/**
 * Control portfolio logger interface; especially for tests
 */
public interface PortfolioLogger {
    void log(WalletEntry walletEntry, BigDecimal entryValue);
}
