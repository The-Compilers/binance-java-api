package com.binance.api.client.limits;

import org.junit.Test;
import static com.binance.api.client.limits.LimitTestingUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for ApiUsage.
 */
public class ApiUsageTest {
  /**
   * Test without any limits, no waiting time.
   */
  @Test
  public void testEmpty() {
    ApiUsage usage = new ApiUsage();
    assertEquals(0, usage.getMinSleepDuration(getTime(), 10));
  }

  /**
   * One limit, 10-second window, no API calls, no wait time.
   */
  @Test
  public void testLimitedNoCalls() {
    ApiUsage usage = new ApiUsage();
    usage.setLimit(10, 10);
    assertEquals(0, usage.getMinSleepDuration(getTime(), 10));
  }

  /**
   * One 10-second limit, one call, does not exceed the limits, no sleep.
   */
  @Test
  public void testOneLimitNotReached() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 1000}, new int[]{5});
    assertEquals(0, usage.getMinSleepDuration(t, 10));
  }

  /**
   * One 10-second limit, three calls, does not exceed limits, no sleep.
   */
  @Test
  public void multipleCallsNoLimitReached() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{2, 2, 2});
    assertEquals(0, usage.getMinSleepDuration(t, 10));
  }

  /**
   * One 10-second limit, three calls, does exceed limits. sleep until the 1st request times out.
   */
  @Test
  public void testSleepUntilFirstTimeout() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{4, 4, 4});
    assertEquals(7000, usage.getMinSleepDuration(t, 10));
  }

  /**
   * One 10-second limit, three calls, does exceed limits, sleep until the 2nd request times out.
   */
  @Test
  public void testSleepUntilSecondTimeout() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{4, 4, 4});
    assertEquals(8000, usage.getMinSleepDuration(t, 15));
    assertEquals(8000, usage.getMinSleepDuration(t, 16));
  }

  /**
   * One 10-second limit, three calls, does exceed limits, sleep until the 3rd request times out.
   */
  @Test
  public void testSleepUntilAllTimeout() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{6, 6, 6});
    assertEquals(9000, usage.getMinSleepDuration(t, 15));
  }

  /**
   * One 10-second limit, three calls, remove old, none of them timed out, sleep until 1st
   * request times out.
   */
  @Test
  public void testRemoveZeroOld() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{6, 6, 6});
    assertEquals(7000, usage.getMinSleepDuration(t, 5));
    usage.forgetOldApiCalls(t);
    assertEquals(7000, usage.getMinSleepDuration(t, 5));
  }

  /**
   * One 10-second limit, three calls, remove old, one of them timed out, no sleep anymore.
   */
  @Test
  public void testRemoveOneNoSleepAnymore() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{6, 6, 6});
    assertEquals(7000, usage.getMinSleepDuration(t, 5));
    usage.forgetOldApiCalls(t + 7200);
    assertEquals(0, usage.getMinSleepDuration(t, 5));
  }


  /**
   * One 10-second limit, three calls, remove old, one of them timed out, sleep until 2nd now
   */
  @Test
  public void testRemoveOneSleepUntilSecond() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{6, 6, 6});
    assertEquals(8000, usage.getMinSleepDuration(t, 10));
    usage.forgetOldApiCalls(t + 7200);
    assertEquals(8000, usage.getMinSleepDuration(t, 10));
  }

  /**
   * One 10-second limit, three calls, remove old, two of them timed out, no sleep anymore.
   * There would be sleep if only one timed out.
   */
  @Test
  public void testRemoveTwoNoSleepAnymore() {
    long t = getTime();
    ApiUsage usage = createUsageWithCalls(20, 10,
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{6, 6, 6});
    assertEquals(8000, usage.getMinSleepDuration(t, 10));
    usage.forgetOldApiCalls(t + 8200);
    assertEquals(0, usage.getMinSleepDuration(t, 10));
  }


  /**
   * Three limits: 10-second limit, 1-minute limit, 1-hour limit. The 10-sec limit is exceeded,
   * wait until the 2nd api call times out.
   */
  @Test
  public void testThreeLimitsSecondLimitReached() {
    long t = getTime();
    ApiUsage usage = createMultiLimitUsageWithCalls(
        new int[]{10, 60, 3600}, new int[]{20, 100, 1000},
        new long[]{t - 3000, t - 2000, t - 1000}, new int[]{6, 6, 6}
    );
    assertEquals(8000, usage.getMinSleepDuration(t, 10));
  }



  /**
   * Three limits: 10-second limit, 1-minute limit, 1-hour limit. The 1-min limit is exceeded,
   * wait until the 1st api call times out.
   */
  @Test
  public void testThreeLimitsMinuteLimitReached() {
    long t = getTime();
    ApiUsage usage = createMultiLimitUsageWithCalls(
        new int[]{10, 60, 3600}, new int[]{20, 100, 1000},
        new long[]{t - 40000, t - 2000, t - 1000}, new int[]{90, 4, 4}
    );
    assertEquals(20000, usage.getMinSleepDuration(t, 10));
  }

  /**
   * Three limits: 10-second limit, 1-minute limit, 1-hour limit. The 1-min limit is exceeded,
   * wait until the 2nd api call times out.
   */
  @Test
  public void testThreeLimitsMinuteLimitReachedSleepUntilSecond() {
    long t = getTime();
    ApiUsage usage = createMultiLimitUsageWithCalls(
        new int[]{10, 60, 3600}, new int[]{20, 100, 1000},
        new long[]{t - 30 * 1000, t - 20 * 1000, t - 1000}, new int[]{4, 90, 4}
    );
    assertEquals(40 * 1000, usage.getMinSleepDuration(t, 10));
  }

  /**
   * Three limits: 10-second limit, 1-minute limit, 1-hour limit. The 1-hour limit is exceeded,
   * wait until the 1st first api call times out.
   */
  @Test
  public void testThreeLimitsHourLimitReached() {
    long t = getTime();
    ApiUsage usage = createMultiLimitUsageWithCalls(
        new int[]{10, 60, 3600}, new int[]{20, 100, 1000},
        new long[]{t - 3000 * 1000, t - 2600 * 1000, t - 1000}, new int[]{4, 990, 4}
    );
    assertEquals(1000 * 1000, usage.getMinSleepDuration(t, 10));
  }
}

