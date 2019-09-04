package ex.ac.wallet;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

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
        Iterator<WalletEntry> entries = walletReader.getEntries(
                toInputStream("a bc=9\nend=8.09"));

        assertEquals(true, entries.hasNext());
        assertEquals("a bc", entries.next().getCurrencyCode());
    }

    private InputStream toInputStream(String formatString, Object... args) {
        try {
            return new ByteArrayInputStream(
                    String.format(formatString, args).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported literal used in test!");
        }
    }

}
