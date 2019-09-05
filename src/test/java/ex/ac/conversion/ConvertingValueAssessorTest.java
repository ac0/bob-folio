package ex.ac.conversion;

import ex.ac.wallet.WalletEntry;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class ConvertingValueAssessorTest {

    @Test
    public void valueOfConversionWithGivenExchangeRate() {
        ValueAssessor valueAssessor = new ConvertingValueAssessor("BASE",
                (fromSym, toSym) -> BigDecimal.TEN);
        WalletEntry walletEntry = new WalletEntry("CUR", "3.2");

        BigDecimal actualValue = valueAssessor.valueOf(walletEntry);

        assertEquals(new BigDecimal("32.0"), actualValue);
    }

    @Test
    public void valueOfWithFluctuatingExchangeRateReturnsSameValueForCurrency() {
        AtomicInteger rate = new AtomicInteger(3);
        ValueAssessor valueAssessor = new ConvertingValueAssessor("BASE",
                (fromSym, toSym) -> BigDecimal.valueOf(rate.getAndIncrement()));
        WalletEntry walletEntry = new WalletEntry("CUR", "2.1");

        valueAssessor.valueOf(new WalletEntry("CUR", "18.009"));
        BigDecimal secondValue = valueAssessor.valueOf(new WalletEntry("CUR", "2.1"));

        assertEquals(new BigDecimal("6.3"), secondValue);
    }

    @Test(expected = UnknownSymbolException.class)
    public void valueOfWithBadSymbolthrowsUnkownSymbolException() {
        ValueAssessor valueAssessor = new ConvertingValueAssessor("BASE",
                (fromSym, toSym) -> {
                    throw new UnknownSymbolException(fromSym);
                });
        WalletEntry walletEntry = new WalletEntry("CUR", "3.2");

        BigDecimal actualValue = valueAssessor.valueOf(walletEntry);

        assertEquals(new BigDecimal("32.0"), actualValue);
    }
}
