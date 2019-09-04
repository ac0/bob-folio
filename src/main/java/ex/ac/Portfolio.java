package ex.ac;

import ex.ac.wallet.WalletEntry;
import ex.ac.wallet.WalletReader;
import ex.ac.wallet.WalletReaderImpl;

import java.io.BufferedInputStream;
import java.util.Iterator;

public class Portfolio {

    public static void main(String... args) {
        WalletReader walletReader = new WalletReaderImpl();
        Iterator<WalletEntry> walletEntries = walletReader.getEntries(new BufferedInputStream(System.in));
        int i = 0;
        while (walletEntries.hasNext()) {
            System.out.printf("%4d. %s%n", ++i, walletEntries.next());
        }
    }
}
