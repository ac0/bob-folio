package ex.ac.conversion;

import ex.ac.wallet.WalletEntry;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A stateful consistent value assessor given a base currency to convert-to and a currency
 * exchange. The consistency property makes sure that one instance will use the same rate
 * between two given currencies through a conversion session
 */
public class ConvertingValueAssessor implements ValueAssessor {
    private final String baseCurrencySymbol;

    private final CurrencyExchange currencyExchange;

    // Number of exchangeRates against the base-currency is manageable to keep caching
    private final ConcurrentMap<String, BigDecimal> exchangeRateMap = new ConcurrentHashMap<>();

    public ConvertingValueAssessor(String baseCurrencyCode, CurrencyExchange currencyExchange) {
        this.baseCurrencySymbol = baseCurrencyCode.toUpperCase();
        this.currencyExchange = currencyExchange;
    }

    @Override
    public BigDecimal valueOf(WalletEntry walletEntry) throws UnknownSymbolException {
        String fromSymbol = walletEntry.getCurrencyCode().toUpperCase();

        BigDecimal exchangeRate = exchangeRateMap.computeIfAbsent(fromSymbol, symbol -> {
            return currencyExchange.getExchangeRate(fromSymbol, baseCurrencySymbol);
        });

        return walletEntry.getAmount().multiply(exchangeRate);
    }
}
