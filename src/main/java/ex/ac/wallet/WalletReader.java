package ex.ac.wallet;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Read wallet data from source(s), typically as a list of entries, each entry
 * representing only one type of currency
 */
public interface WalletReader {
    Iterator<WalletEntry> getEntries(InputStream fis);

}
