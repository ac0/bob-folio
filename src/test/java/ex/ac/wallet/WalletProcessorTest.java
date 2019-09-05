package ex.ac.wallet;

import ex.ac.PortfolioEntryException;
import ex.ac.PortfolioLogger;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class WalletProcessorTest {

    private WalletProcessor walletProcessor = new WalletProcessorImpl();

    @Test
    public void testSingleRecordPrint() {
        WalletEntry record = new WalletEntry("abc", "34.2");

        AtomicBoolean wasItemProcessed = new AtomicBoolean(false);
        walletProcessor.valueOf(Collections.singleton(record).iterator(), WalletEntry::getAmount, new PortfolioMockLogger() {
            @Override
            public void logEntry(WalletEntry walletEntry, BigDecimal entryValue) {
                assertEquals(record, walletEntry);
                wasItemProcessed.set(true);
            }
        });

        assertTrue("Failed to process entry " + record, wasItemProcessed.get());
    }

    @Test
    public void testAllRecordsProcessedSuccessfully() {
        List<WalletEntry> WalletEntrys = new ArrayList<>();
        WalletEntrys.add(new WalletEntry("ab", "2"));
        WalletEntrys.add(new WalletEntry("bc", "0.4"));

        AtomicInteger printCounter = new AtomicInteger(0);
        walletProcessor.valueOf(WalletEntrys.iterator(), WalletEntry::getAmount, new PortfolioMockLogger() {
            @Override
            public void logEntry(WalletEntry walletEntry, BigDecimal entryValue) {
                printCounter.incrementAndGet();
            }

        });

        assertEquals(WalletEntrys.size(), printCounter.get());
    }

    private static class PortfolioMockLogger implements PortfolioLogger {
        @Override
        public void logEntry(WalletEntry walletEntry, BigDecimal entryValue) {
        }

        @Override
        public void logSummary(BigDecimal totalValue, String valueCode) {

        }

        @Override
        public void logErrorEntry(PortfolioEntryException entryError, boolean summarisedAbort) {

        }
    }

    @Test
    public void testTotalling() {
        List<WalletEntry> walletEntries = new ArrayList<>();
        walletEntries.add(new WalletEntry("ab", "2.98"));
        walletEntries.add(new WalletEntry("bc", "17.3"));
        walletEntries.add(new WalletEntry("def", "-0.4"));
        BigDecimal expectedTotal = walletEntries.stream()
                .map(WalletEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal actualTotal = walletProcessor.valueOf(walletEntries.iterator(),
                WalletEntry::getAmount, new PortfolioMockLogger());

        assertEquals(expectedTotal, actualTotal);
    }
}
