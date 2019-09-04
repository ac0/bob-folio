package ex.ac;

import ex.ac.wallet.WalletReader;
import ex.ac.wallet.WalletReaderImpl;

import java.io.BufferedInputStream;
import java.util.Iterator;

public class Portfolio {

    public static void main(String... args) {
        WalletReader walletReader = new WalletReaderImpl();
        Iterator<String> lines = walletReader.getLines(new BufferedInputStream(System.in));
        int i = 0;
        while (lines.hasNext()) {
            System.out.printf("%4d. %s%n", ++i, lines.next());
        }
    }
}
