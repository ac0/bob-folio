package ex.ac.wallet;

import ex.ac.PortfolioLogger;

import java.util.Iterator;

/**
 * Processing methods on the effective Wallet like the list of wallet entries. Can later
 * include processing method(s) of the singlular wallet entries too
 */
public interface WalletProcessor {
    void process(Iterator<WalletEntry> walletEntries, ValueAssessor valueAssessor, PortfolioLogger portfolioLogger);
}
