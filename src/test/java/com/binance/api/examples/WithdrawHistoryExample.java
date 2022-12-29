package com.binance.api.examples;

import com.binance.api.client.constant.Util;

/**
 * Example getting withdraw history.
 */
public class WithdrawHistoryExample extends AuthenticatedExampleBase {
  public static void main(String[] args) {
    WithdrawHistoryExample example = new WithdrawHistoryExample();
    example.run();
  }

  private void run() {
    String startTimeString = "2019-11-01 00:00:00";
    String endTimeString = "2019-11-30 23:59:59";
    System.out.println("Withdrawals for LTC " + startTimeString + " - " + endTimeString);
    long startTime = Util.getTimestampFor(startTimeString);
    long endTime = Util.getTimestampFor(endTimeString);
    System.out.println(client.getWithdrawHistory("LTC", null, null, null, null,
        startTime, endTime));
    System.out.println();
  }
}
