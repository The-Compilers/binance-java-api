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
   * @return TimeRange from 00:00:00 on the first day until 23:59:59 on the last day of the month
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
    return TimeRange.createForMonth(month, year);
  }

  /**
   * Create a time range for the given month.
   *
   * @param month The month as an integer in the range 1-12
   * @param year  The year as a four-digit integer
   * @return TimeRange from 00:00:00 on the first day until 23:59:59 on the last day of the month
   * @throws IllegalArgumentException If the provided month or year is invalid
   */
  public static TimeRange createForMonth(int month, int year) throws IllegalArgumentException {
    if (month < 1 || month > 12) throw new IllegalArgumentException("Invalid month: " + month);
    if (year < 1000 || year > 9999) throw new IllegalArgumentException("Invalid year: " + year);

    int lastDayOfMonth = getDaysInMonth(year, month);
    String startString = year + "-" + month + "-01 00:00:00";
    String endString = year + "-" + month + "-" + lastDayOfMonth + " 23:59:59";

    return new TimeRange(Util.getTimestampFor(startString), Util.getTimestampFor(endString));
  }

  /**
   * Create a time range for a given day.
   *
   * @param day   The day as an integer
   * @param month The month as an integer in the range 1-12
   * @param year  The year as a four-digit integer
   * @return TimeRange from 00:00:00 on the first day until 23:59:59 on the last day of the month
   * @throws IllegalArgumentException If the provided month or year is invalid
   */
  public static TimeRange createForDay(int day, int month, int year) {
    if (month < 1 || month > 12) throw new IllegalArgumentException("Invalid month: " + month);
    if (year < 1000 || year > 9999) throw new IllegalArgumentException("Invalid year: " + year);
    if (day < 1 || day > getDaysInMonth(year, month)) {
      throw new IllegalArgumentException("Invalid dat: " + month);
    }

    String dayString = year + "-" + month + "-" + day;
    String startString = dayString + " 00:00:00";
    String endString = dayString + " 23:59:59";

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

  /**
   * Get the number of days in a given month.
   *
   * @param year  The year to consider
   * @param month The month to consider
   * @return The number of days in the given month: from 28 to 31
   */
  public static int getDaysInMonth(int year, int month) {
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


