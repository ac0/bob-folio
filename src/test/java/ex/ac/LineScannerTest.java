package ex.ac;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class LineScannerTest {

    LineScanner lineScanner = new LineScannerImpl();

    @Test
    public void getLinesSingleWordLines() {
        Iterator<String> lines = lineScanner.getLines(
                toInputStream("abc%ndef"));

        assertEquals(true, lines.hasNext());
        assertEquals("abc", lines.next());
        assertEquals("def", lines.next());
        assertEquals(false, lines.hasNext());
    }

    @Test
    public void getLinesMultiWord() throws UnsupportedEncodingException {
        Iterator<String> lines = lineScanner.getLines(
                toInputStream("a bc%nend"));

        assertEquals(true, lines.hasNext());
        assertEquals("a bc", lines.next());
    }

    private InputStream toInputStream(String formatString, Object... args) {
        try {
            return new ByteArrayInputStream(
                    String.format(formatString, args).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported literal used in test!");
        }
    }

}
