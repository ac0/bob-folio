package ex.ac.wallet;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalletReaderImpl implements WalletReader {

    // Non negative entries
    private static final String WALLET_ENTRY_PATTERN = "\\s*([A-Za-z]*)\\s*=\\s*([0-9]*\\.?[0-9]*)\\s*";

    private final Pattern entryPattern;

    public WalletReaderImpl() {
        this(WALLET_ENTRY_PATTERN);
    }

    public WalletReaderImpl(String entryPattern) {
        this.entryPattern = Pattern.compile(entryPattern);
    }

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
                String line = scanner.next();
                Matcher matcher = entryPattern.matcher(line);
                if(!matcher.matches()){
                    throw new InvalidWalletEntryException("Invalid entry: " + line);
                }

                return new WalletEntry(matcher.group(1), matcher.group(2));
            }
        };
    }
}
