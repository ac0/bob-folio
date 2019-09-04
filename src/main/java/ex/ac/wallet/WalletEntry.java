package ex.ac.wallet;

import java.math.BigDecimal;

/**
 * Models a wallet entry of any type of currency and an amount
 */
public class WalletEntry {

    private String currencyCode;

    private BigDecimal amount;

    public WalletEntry(String currencyCode, String amount) {
        this.currencyCode = currencyCode;
        this.amount = new BigDecimal(amount);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%7s * %-10s", currencyCode, amount);
    }
}
