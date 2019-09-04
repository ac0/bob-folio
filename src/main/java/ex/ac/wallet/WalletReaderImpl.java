package ex.ac.wallet;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class WalletReaderImpl implements WalletReader {

    public Iterator<WalletEntry> getEntries(InputStream fis) {
        Scanner scanner = new Scanner(fis)
                .useDelimiter(System.lineSeparator());

        return new Iterator<WalletEntry>() {
            @Override
            public boolean hasNext() {
                return scanner.hasNext();
            }

            @Override
            public WalletEntry next() {
                String[] tokens = scanner.next().split("=");
                return new WalletEntry(tokens[0], tokens[1]);
            }
        };
    }
}
