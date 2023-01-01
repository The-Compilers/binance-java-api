package com.binance.api.client.constant;

import java.time.YearMonth;

/**
 * Represents a range of timestamps: start time and end time
 */
public class TimeRange {
  private long startTime;
  private long endTime;

  /**
   * Create a new time range.
   *
   * @param startTime The start time, including milliseconds
   * @param endTime   The end time, including milliseconds
   */
  public TimeRange(long startTime, long endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Create a time range for the given month.
   *
   * @param monthString The month as a string, in the format YYYY-mm
   * @return
   * @throws IllegalArgumentException If the provided month string is invalid
   */
  public static TimeRange createForMonth(String monthString) throws IllegalArgumentException {
    if (monthString == null) throw new IllegalArgumentException("monthString can't be null");
    String[] parts = monthString.split("-");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid monthString format");
    }

    int year = parseInteger(parts[0]);
    int month = parseInteger(parts[1]);
    int lastDayOfMonth = getDaysInMonth(year, month);
    String startString = year + "-" + month + "-01 00:00:00";
    String endString = year + "-" + month + "-" + lastDayOfMonth + " 23:59:59";

    return new TimeRange(Util.getTimestampFor(startString), Util.getTimestampFor(endString));
  }

  /**
   * Get the start time of the range, including milliseconds.
   *
   * @return The start time
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * Get end time of the range, including milliseconds.
   *
   * @return The end time
   */
  public long getEndTime() {
    return endTime;
  }

  private static int getDaysInMonth(int year, int month) {
    YearMonth yearMonthObject = YearMonth.of(year, month);
    return yearMonthObject.lengthOfMonth();
  }

  private static int parseInteger(String numberString) throws IllegalArgumentException {
    try {
      return Integer.parseInt(numberString);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("This was expected to be a number: " + numberString);
    }
  }
}


