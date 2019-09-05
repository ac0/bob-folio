package ex.ac.conversion;

import ex.ac.wallet.WalletEntry;

import java.math.BigDecimal;

/**
 * Value assessment of Wallet entities. Could be a normalised value
 */
public interface ValueAssessor {
    BigDecimal valueOf(WalletEntry walletEntry);
}
