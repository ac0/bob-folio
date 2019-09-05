package ex.ac.conversion;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Adapter test. Serves as acceptability tests to certify third-party's API via adapter
 */
public class CryptoCompareExchangeAdaptorTest {
    CurrencyExchange converter = new CryptoCompareExchangeAdaptor();

    @Test
    public void getExchangeRateValid() throws UnknownSymbolException {
        BigDecimal maxFluctuationExpected = BigDecimal.valueOf(1.1);

        BigDecimal to = converter.getExchangeRate("BTC", "EUR");
        BigDecimal fro = converter.getExchangeRate("EUR", "BTC");
        BigDecimal product = to.multiply(fro);

        assertTrue("Too big a difference? " + product,
                product.compareTo(maxFluctuationExpected) < 0);
    }

    @Test(expected = UnknownSymbolException.class)
    public void getExchangeRateWithBadFromSym() throws UnknownSymbolException {
        BigDecimal toVal = converter.getExchangeRate("BTOONO", "EUR");
    }
}
