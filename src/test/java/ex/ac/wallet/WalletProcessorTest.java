package ex.ac.wallet;

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
        walletProcessor.process(Collections.singleton(record).iterator(), WalletEntry::getAmount, new PortfolioLogger() {
            @Override
            public void log(WalletEntry walletEntry, BigDecimal entryValue) {
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
        walletProcessor.process(WalletEntrys.iterator(), WalletEntry::getAmount, new PortfolioLogger() {
            @Override
            public void log(WalletEntry walletEntry, BigDecimal entryValue) {
                printCounter.incrementAndGet();
            }

        });

        assertEquals(WalletEntrys.size(), printCounter.get());
    }
}
