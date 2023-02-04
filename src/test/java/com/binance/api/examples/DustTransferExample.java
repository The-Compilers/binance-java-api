package com.binance.api.examples;

import com.binance.api.client.constant.TimeRange;
import com.binance.api.client.domain.account.dust.DustTransferLog;
import com.binance.api.examples.helper.AuthenticatedExampleBase;
import org.apache.log4j.PropertyConfigurator;

/**
 * Example of Dust transfer endpoints
 */
public class DustTransferExample extends AuthenticatedExampleBase {
  public static void main(String[] args) {
    PropertyConfigurator.configure("log4j.properties");
    DustTransferExample example = new DustTransferExample();
    example.run();
  }

  private void run() {
    printDustTransferLog("2020-09");
    printDustTransferLog("2022-01");
    printRecentDustTransferLog();
  }

  private void printRecentDustTransferLog() {
    printDustTransferLog(null);
  }

  private void printDustTransferLog(String month) {
    Long startTime = null;
    Long endTime = null;
    String rangeTitle;

    if (month != null) {
      rangeTitle = month;
      TimeRange range = TimeRange.createForMonth(month);
      startTime = range.getStartTime();
      endTime = range.getEndTime();
    } else {
      rangeTitle = "Recent";
    }

    System.out.println(rangeTitle + " dust log");

    DustTransferLog dustLog = client.getDustTransferHistory(startTime, endTime);
    System.out.println(dustLog);
    System.out.println();
  }
}
