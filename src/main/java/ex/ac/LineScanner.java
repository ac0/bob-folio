package ex.ac;

import java.io.InputStream;
import java.util.Iterator;

public interface LineScanner {
    Iterator<String> getLines(InputStream fis);

}
