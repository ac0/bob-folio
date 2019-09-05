package ex.ac;

import ex.ac.wallet.WalletEntry;

/**
 * Control portfolio logger interface; especially for tests
 */
public interface PortfolioLogger {
    void log(WalletEntry walletEntry);
}
