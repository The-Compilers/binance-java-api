package com.binance.api.client.limits;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A 'bucket' of all API calls within a specific time window, with specific allowed weight limit.
 */
public class ApiCallBucket {
  private long duration;
  private int maxWeight;

  private int usedWeight;

  private final Queue<ApiCallEntry> apiCalls = new LinkedList<>();

  /**
   * Create a new API call log.
   *
   * @param maxWeight maximum allowed weight within the specified duration
   * @param duration  The duration of the sliding time window, in milliseconds
   */
  public ApiCallBucket(int maxWeight, long duration) {
    this.maxWeight = maxWeight;
    this.duration = duration;
  }

  /**
   * Get minimum sleep duration necessary when a request with specified weight will be allowed.
   *
   * @param apiCallTime The timestamp when the new request is sent, used as a reference point
   * @param weight      The weight of a new request to send
   * @return The necessary sleep duration, in milliseconds
   */
  public long getMinSleepDuration(long apiCallTime, int weight) {
    if (weight > maxWeight) {
      throw new IllegalStateException("Can't send request with weight=" + weight
          + " when maxWeight=" + maxWeight);
    }

    int newWeightAfterRequest = usedWeight + weight;
    int minWeightToRelease = newWeightAfterRequest - maxWeight;

    long minSleepTime = 0;
    if (minWeightToRelease > 0) {
      minSleepTime = getMinSleepTimeToRelease(apiCallTime, minWeightToRelease);
    }

    System.out.println("maxWeight=" + maxWeight + ", duration=" + duration
        + ", usedWeight=" + usedWeight + ", weight=" + weight + ", minSleep=" + minSleepTime);

    return minSleepTime;
  }

  private long getMinSleepTimeToRelease(long apiCallTime, int minWeightToRelease) {
    long timestampOfLastRequest = 0;
    int releasedWeight = 0;
    Iterator<ApiCallEntry> it = apiCalls.iterator();

    while (releasedWeight < minWeightToRelease && it.hasNext()) {
      ApiCallEntry e = it.next();
      timestampOfLastRequest = e.getTime();
      releasedWeight += e.getWeight();
    }

    long timeWhenEnoughWeightIsReleased = timestampOfLastRequest + duration;
    long minSleepDuration = timeWhenEnoughWeightIsReleased - apiCallTime;
    if (minSleepDuration < 0) {
      minSleepDuration = 0;
    }

    return minSleepDuration;
  }

  /**
   * Add a new API call to the log.
   *
   * @param entry The new API call entry to add
   */
  public void addCall(ApiCallEntry entry) {
    apiCalls.add(entry);
    usedWeight += entry.getWeight();
  }

  /**
   * Forget old API calls whose weight don't matter anymore.
   *
   * @param timeNow The time "now" - used to decide which API calls have timed out now
   */
  public void forgetOldApiCalls(long timeNow) {
    Iterator<ApiCallEntry> it = apiCalls.iterator();
    while (it.hasNext()) {
      ApiCallEntry e = it.next();
      if (hasExpired(e, timeNow)) {
        System.out.println("Api call expired, duration=" + duration + ", weight=" + e.getWeight());
        usedWeight -= e.getWeight();
        it.remove();
      }
    }
  }

  private boolean hasExpired(ApiCallEntry e, long timeNow) {
    return e.getTime() + duration < timeNow;
  }
}
