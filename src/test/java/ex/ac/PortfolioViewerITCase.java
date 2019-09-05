package ex.ac;

import ex.ac.wallet.WalletReaderTest;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

/**
 * Just basic overall testing with all the real pieces of the app put together, of course with the
 * exception of the single 3rd party external runtime dependency, the crypto-exchange
 */
public class PortfolioViewerITCase {

    @Test
    public void printPortFolioWithMockedCurrencyExchange() throws FileNotFoundException {
        // user input
        InputStream sourceStream = WalletReaderTest.toInputStream(
                "BTX=10%n"
                        + "ETN=5");

        // mocked exchange
        PortfolioViewer portfolioViewer = new PortfolioApp((fromSym, toSym) -> {
            switch (fromSym) {
                case "BTX":
                    return new BigDecimal("2.5");
                case "ETN":
                    return new BigDecimal(2);
                default:
                    return new BigDecimal(1);
            }
        });

        // expected from above input and mock
        BigDecimal expectedTotal = new BigDecimal("10").multiply(new BigDecimal("2.5"));
        expectedTotal = new BigDecimal("5").multiply(new BigDecimal("2")).add(expectedTotal);

        // result capturer
        AtomicReference<BigDecimal> actualTotal = new AtomicReference<>();
        PortfolioLogger portfolioLogger = new ConsolePortfolioLogger(System.out) {
            @Override
            public void logSummary(BigDecimal totalValue, String valueCode) {
                super.logSummary(totalValue, valueCode);
                actualTotal.set(totalValue);
            }
        };

        portfolioViewer.printPortfolio(sourceStream, portfolioLogger);

        assertNotNull(actualTotal.get());
        assertEquals(expectedTotal, actualTotal.get());
    }
}
