package ex.ac;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class LineScannerImpl implements LineScanner {

    public Iterator<String> getLines(InputStream fis) {
        Scanner scanner = new Scanner(fis)
                .useDelimiter(System.lineSeparator());

        return scanner;
    }
}
