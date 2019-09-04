package ex.ac;

import java.io.BufferedInputStream;
import java.util.Iterator;

public class Portfolio {

    public static void main(String... args) {
        LineScanner lineScanner = new LineScannerImpl();
        Iterator<String> lines = lineScanner.getLines(new BufferedInputStream(System.in));
        int i = 0;
        while (lines.hasNext()) {
            System.out.printf("%4d. %s%n", ++i, lines.next());
        }
    }
}
