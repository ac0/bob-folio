package ex.ac.wallet;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class WalletReaderImpl implements WalletReader {

    public Iterator<String> getLines(InputStream fis) {
        Scanner scanner = new Scanner(fis)
                .useDelimiter(System.lineSeparator());

        return scanner;
    }
}
