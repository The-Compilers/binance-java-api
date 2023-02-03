package com.binance.api.client.limits;

import java.util.HashMap;
import java.util.Map;

/**
 * Usage of API calls with specific type, with several possible time windows.
 */
public class ApiUsage {
  private final Map<Integer, ApiCallBucket> buckets = new HashMap<>();

  /**
   * Set a limit of maximum allowed weight w every s seconds.
   *
   * @param maxWeight  Maximum allowed weight in the time window
   * @param perSeconds The length of the time window, in seconds
   * @return This object, for chained method calls
   */
  public ApiUsage setLimit(int maxWeight, int perSeconds) {
    buckets.put(perSeconds, new ApiCallBucket(maxWeight, perSeconds * 1000L));
    return this;
  }

  /**
   * Check how long we would need to sleep before a new API call with given weight could be sent.
   *
   * @param timestamp Time moment at which the API call is sent
   * @param weight    The weight of the API call to send.
   * @return Minimum sleep duration in milliseconds, zero if no sleep is necessary
   */
  public long getMinSleepDuration(long timestamp, int weight) {
    long minSleepDuration = 0;
    for (ApiCallBucket bucket : buckets.values()) {
      long d = bucket.getMinSleepDuration(timestamp, weight);
      minSleepDuration = Math.max(minSleepDuration, d);
    }
    return minSleepDuration;
  }

  /**
   * Register a new API call in the log.
   *
   * @param time   The time when the call was made, Unix timestamp, with milliseconds.
   * @param weight The weight of the call
   */
  public void addCall(long time, int weight) {
    ApiCallEntry entry = new ApiCallEntry(time, weight);
    for (ApiCallBucket bucket : buckets.values()) {
      bucket.addCall(entry);
    }
  }

  /**
   * Forget old API calls whose weight don't matter anymore. Consider all limits (all time windows).
   *
   * @param timeNow The time "now" - used to decide which API calls have timed out now
   */
  public void forgetOldApiCalls(long timeNow) {
    for (ApiCallBucket bucket : buckets.values()) {
      bucket.forgetOldApiCalls(timeNow);
    }
  }
}
