package ex.ac.wallet;

import java.io.InputStream;
import java.util.Iterator;

public interface WalletReader {
    Iterator<String> getLines(InputStream fis);

}
