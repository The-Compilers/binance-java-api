package com.binance.api.examples;

import com.binance.api.client.constant.TimeRange;

/**
 * Example getting withdraw history.
 */
public class DepositAndWithdrawHistoryExample extends AuthenticatedExampleBase {
  public static void main(String[] args) {
    DepositAndWithdrawHistoryExample example = new DepositAndWithdrawHistoryExample();
    example.run();
  }

  private void run() {
    printCoinDepositHistory();
    printFiatDepositHistory();
    printCoinWithdrawHistory();
    printFiatWithdrawHistory();
  }

  private void printCoinDepositHistory() {
    printDeposits("LTC", "2020-03");
  }

  private void printFiatDepositHistory() {
    printDeposits(null, "2020-03");
  }

  private void printCoinWithdrawHistory() {
    printWithdrawals("LTC", "2019-11");
  }

  private void printFiatWithdrawHistory() {
    printWithdrawals(null, "2020-10");
  }

  private void printDeposits(String asset, String month) {
    printTransactions(asset, month, true);
  }

  private void printWithdrawals(String asset, String month) {
    printTransactions(asset, month, false);
  }

  private void printTransactions(String asset, String month, boolean deposit) {
    boolean isFiat = asset == null;
    String title = (isFiat ? "Fiat" : asset) + (deposit ? " deposits" : " withdrawals")
        + " for " + month;
    System.out.println(title);
    TimeRange range = TimeRange.createForMonth(month);
    Object history;
    if (deposit) {
      if (isFiat) {
        history = client.getFiatDepositHistory(range.getStartTime(), range.getEndTime());
      } else {
        history = client.getDepositHistory(asset, null, range.getStartTime(), range.getEndTime(),
            null, null);
      }
    } else {
      if (isFiat) {
        history = client.getFiatWithdrawHistory(range.getStartTime(), range.getEndTime());
      } else {
        history = client.getWithdrawHistory(asset, null, null, null, null,
            range.getStartTime(), range.getEndTime());
      }
    }
    System.out.println(history);
    System.out.println();
  }
}
