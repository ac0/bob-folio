package ex.ac.wallet;

import java.math.BigDecimal;

public interface ValueAssessor {
    BigDecimal valueOf(WalletEntry walletEntry);
}
