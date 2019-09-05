package ex.ac.conversion;

import java.math.BigDecimal;

/**
 * Cross currency functions like exchange rates
 */
public interface CurrencyExchange {
    BigDecimal getExchangeRate(String fromSym, String toSym) throws UnknownSymbolException;
}
