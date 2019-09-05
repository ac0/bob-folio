package ex.ac;

import ex.ac.wallet.WalletEntry;

/**
 * Console output implementation. Sink, hence left out of test net. Not much to gain forced mocking
 * of out.printf and limit class from using say another method
 */
public class ConsolePortfolioLogger implements PortfolioLogger {

    private int count;

    @Override
    public void log(WalletEntry walletEntry) {
        System.out.printf("%4d. %s%n", ++count, walletEntry);
    }
}
