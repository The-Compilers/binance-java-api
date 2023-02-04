package com.binance.api.examples;

import com.binance.api.client.constant.TimeRange;
import com.binance.api.examples.helper.AuthenticatedExampleBase;

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
    printCoinWithdrawHistory();
  }

  private void printCoinDepositHistory() {
    printDeposits("LTC", "2020-03");
  }

  private void printCoinWithdrawHistory() {
    printWithdrawals("LTC", "2019-11");
  }


  private void printDeposits(String asset, String month) {
    printTransactions(asset, month, true);
  }

  private void printWithdrawals(String asset, String month) {
    printTransactions(asset, month, false);
  }

  private void printTransactions(String asset, String month, boolean deposit) {
    String title = asset + (deposit ? " deposits" : " withdrawals") + " for " + month;
    System.out.println(title);
    TimeRange range = TimeRange.createForMonth(month);
    Object history; // For printing any type is OK
    if (deposit) {
      history = client.getDepositHistory(asset, null, range.getStartTime(), range.getEndTime(),
          null, null);
    } else {
      history = client.getWithdrawHistory(asset, null, null, null, null,
          range.getStartTime(), range.getEndTime());
    }
    System.out.println(history);
    System.out.println();
  }
}
