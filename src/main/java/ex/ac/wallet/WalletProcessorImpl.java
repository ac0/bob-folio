package ex.ac.wallet;

import ex.ac.PortfolioLogger;

import java.util.Iterator;

public class WalletProcessorImpl implements WalletProcessor {
    @Override
    public void process(Iterator<WalletEntry> walletEntries, PortfolioLogger portfolioLogger) {
        while (walletEntries.hasNext()) {
            portfolioLogger.log(walletEntries.next());
        }
    }

}
