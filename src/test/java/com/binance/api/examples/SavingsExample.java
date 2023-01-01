package com.binance.api.examples;

import com.binance.api.client.constant.TimeRange;
import com.binance.api.client.domain.account.LendingType;
import com.binance.api.client.domain.account.SavingsInterest;
import java.util.List;

/**
 * Examples of using Savings-endpoints.
 */
public class SavingsExample extends AuthenticatedExampleBase {
  public static void main(String[] args) {
    SavingsExample example = new SavingsExample();
    example.run();
  }

  private void run() {
    printInterest("BTC", "2022-12");
    printInterest("ETH", "2022-11");
  }

  private void printInterest(String asset, String month) {
    TimeRange range = TimeRange.createForMonth(month);
    List<SavingsInterest> interests = client.getSavingsInterestHistory(LendingType.DAILY,
        asset, range.getStartTime(), range.getEndTime());
    System.out.println(asset + " savings interests: ");
    System.out.println(interests);
    System.out.println();
  }
}
