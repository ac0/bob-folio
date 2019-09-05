package ex.ac.wallet;

import ex.ac.PortfolioLogger;

import java.math.BigDecimal;
import java.util.Iterator;

public class WalletProcessorImpl implements WalletProcessor {
    @Override
    public void process(Iterator<WalletEntry> walletEntries, ValueAssessor valueAssessor, PortfolioLogger portfolioLogger) {
        while (walletEntries.hasNext()) {
            WalletEntry walletEntry = walletEntries.next();
            BigDecimal entryValue = valueAssessor.valueOf(walletEntry);

            portfolioLogger.log(walletEntry, entryValue);
        }
    }

}
