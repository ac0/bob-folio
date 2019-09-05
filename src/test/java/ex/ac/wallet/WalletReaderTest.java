package ex.ac.wallet;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;

import static org.junit.Assert.*;

public class WalletReaderTest {

    WalletReader walletReader = new WalletReaderImpl();

    @Test
    public void getEntriesCodesNoSpaces() {
        Iterator<WalletEntry> entries = walletReader.getEntries(
                toInputStream("abc=9.8%ndef=7.3"));

        assertEquals(true, entries.hasNext());
        assertEquals(new BigDecimal("9.8"), entries.next().getAmount());
        assertEquals("def", entries.next().getCurrencyCode());
        assertEquals(false, entries.hasNext());
    }

    @Test
    public void getEntriesCodeWithASpace() {
        // reader by passing explicit pattern param to support this existing test
        WalletReader walletReader = new WalletReaderImpl("(.*)=(.*)");

        Iterator<WalletEntry> entries = walletReader.getEntries(
                toInputStream("a bc=9\nend=8.09"));

        assertEquals(true, entries.hasNext());
        assertEquals("a bc", entries.next().getCurrencyCode());
    }

    @Test
    public void getEntriesTolerateExtraOrTrailingSpace() {
        Iterator<WalletEntry> entries = walletReader.getEntries(
                toInputStream("around = 9%n beginning=8.09%ntrailing=9.88 "));

        WalletEntry lastEntry = null;
        while (entries.hasNext()) {
            lastEntry = entries.next();// main test - implied with a non-throw
        }

        assertNotNull(lastEntry);
        assertEquals(new BigDecimal("9.88"), lastEntry.getAmount());
    }

    @Test(expected = InvalidWalletEntryException.class)
    public void getEntriesOnBadCurrencyCodeThrowInvalidEntryException() {
        Iterator<WalletEntry> entries = walletReader.getEntries(
                toInputStream("sbd-ddf=9.88"));

        while (entries.hasNext()) {
            entries.next();
        }
    }

    @Test(expected = InvalidWalletEntryException.class)
    public void getEntriesOnBadFormattedAmountThrowInvalidEntryException() {
        Iterator<WalletEntry> entries = walletReader.getEntries(
                toInputStream("adb=9.8-8"));

        while (entries.hasNext()) {
            entries.next();
        }
    }

    public static InputStream toInputStream(String formatString, Object... args) {
        try {
            return new ByteArrayInputStream(
                    String.format(formatString, args).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported literal used in test!");
        }
    }

}
