package ex.ac.wallet;

import ex.ac.PortfolioLogger;
import ex.ac.conversion.ValueAssessor;

import java.math.BigDecimal;
import java.util.Iterator;

public class WalletProcessorImpl implements WalletProcessor {
    @Override
    public BigDecimal valueOf(Iterator<WalletEntry> walletEntries,
                              ValueAssessor valueAssessor, PortfolioLogger portfolioLogger) {
        BigDecimal totalValue = BigDecimal.ZERO;

        while (walletEntries.hasNext()) {
            WalletEntry walletEntry = walletEntries.next();
            BigDecimal entryValue = valueAssessor.valueOf(walletEntry);
            totalValue = totalValue.add(entryValue);

            portfolioLogger.logEntry(walletEntry, entryValue);
        }

        return totalValue;
    }

}
