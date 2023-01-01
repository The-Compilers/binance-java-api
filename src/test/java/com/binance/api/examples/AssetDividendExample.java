package com.binance.api.examples;

import com.binance.api.client.constant.TimeRange;
import com.binance.api.client.domain.account.AssetDividendHistory;

/**
 * Examples for asset dividend endpoint(s).
 */
public class AssetDividendExample extends AuthenticatedExampleBase {
  public static void main(String[] args) {
    AssetDividendExample example = new AssetDividendExample();
    example.run();
  }

  private void run() {
    printAssetDividend("ETHW", null);
    printAssetDividend("NFT", "2022-11");
  }

  private void printAssetDividend(String asset, String month) {
    String periodTitle;
    Long startTime = null;
    Long endTime = null;

    if (month != null) {
      periodTitle = month;
      TimeRange range = TimeRange.createForMonth(month);
      startTime = range.getStartTime();
      endTime = range.getEndTime();
    } else {
      periodTitle = "Recent";
    }

    System.out.println(periodTitle + " asset dividend for " + asset);
    AssetDividendHistory dividends = client.getAssetDividendHistory(asset, startTime, endTime);
    System.out.println(dividends);
    System.out.println();
  }
}
