package com.binance.api.client.limits;

import org.jetbrains.annotations.NotNull;
import static org.junit.Assert.assertEquals;

/**
 * Utility class for different limit-related tests
 */
public class LimitTestingUtils {
  /**
   * Get the current system time, in milliseconds.
   *
   * @return Current system time, in milliseconds.
   */
  protected static long getTime() {
    return System.currentTimeMillis();
  }

  /**
   * Create ApiUsage with a single bucket
   *
   * @param maxWeight      Maximum weight in the bucket
   * @param perSeconds     Duration of the time window for the bucket
   * @param apiCallTimes   The time moments when API calls were made
   * @param apiCallWeights The weights of the API calls
   * @return The ApiUsage object
   */
  @NotNull
  protected static ApiUsage createUsageWithCalls(int maxWeight, int perSeconds,
                                                 long[] apiCallTimes, int[] apiCallWeights) {
    return createMultiLimitUsageWithCalls(new int[]{perSeconds}, new int[]{maxWeight},
        apiCallTimes, apiCallWeights);
  }

  /**
   * Create ApiUsage with specific limits, specific API calls.
   *
   * @param timeLimits     The durations (time limits) of the different buckets
   * @param limitWeights   The weights of the different buckets
   * @param apiCallTimes   The time moments when API calls were made
   * @param apiCallWeights The weights of the API calls
   * @return The ApiUsage object
   */
  protected static ApiUsage createMultiLimitUsageWithCalls(
      int[] timeLimits, int[] limitWeights,
      long[] apiCallTimes, int[] apiCallWeights) {
    assertEquals(timeLimits.length, limitWeights.length);
    assertEquals(apiCallTimes.length, apiCallWeights.length);
    ApiUsage usage = new ApiUsage();

    for (int i = 0; i < timeLimits.length; ++i) {
      usage.setLimit(limitWeights[i], timeLimits[i]);
    }
    for (int j = 0; j < apiCallWeights.length; ++j) {
      usage.addCall(apiCallTimes[j], apiCallWeights[j]);
    }
    return usage;
  }
}
