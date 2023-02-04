package com.binance.api.client.limits;

import java.util.EnumMap;
import java.util.Map;

/**
 * Keeps account of the API requests sent, their weights, used quotas.
 * Thread-safe.
 * When a next request is ready, the accountant can tell how long we need to wait until this
 * request can be sent.
 * A singleton class, because we want to be sure that the IP-related limits are kept even when
 * there are several REST clients, in separate threads.
 */
public class ApiLimitAccountant {
  private static final ApiLimitAccountant instance = new ApiLimitAccountant();

  private final Map<ApiLimitType, ApiUsage> usages = new EnumMap<>(ApiLimitType.class);

  /**
   * Get a singleton instance of the class.
   *
   * @return The singleton instance
   */
  public static ApiLimitAccountant getInstance() {
    return instance;
  }

  /**
   * Create an accountant with the default rate limits, according to the docs.
   * https://binance-docs.github.io/apidocs/spot/en/#limits
   */
  private ApiLimitAccountant() {
    registerDefaultLimits();
  }

  private void registerDefaultLimits() {
    usages.put(ApiLimitType.API_IP, new ApiUsage().setLimit(1200, 60).setLimit(6100, 5 * 60));
    usages.put(ApiLimitType.ORDER, new ApiUsage().setLimit(50, 10).setLimit(160000, 24 * 60 * 60));
    usages.put(ApiLimitType.SAPI_IP, new ApiUsage().setLimit(12000, 60));
    usages.put(ApiLimitType.SAPI_UID, new ApiUsage().setLimit(180000, 60));
  }

  /**
   * Find the minimum sleep duration before the next request can be sent, according to the
   * currently used rate limits
   *
   * @param weights   The weights of the request (API call) to be made
   * @param timestamp The time moment to consider (the `now`).
   *                  Note: this can be set so that it is easier to test.
   * @return The sleep duration or null if no sleep is necessary.
   */
  public long getMinSleepDuration(ApiCallWeights weights, long timestamp) {
    long minSleepDuration = 0;
    for (ApiLimitType type : weights.getQuotaTypes()) {
      int weight = weights.getWeight(type);
      long sleepDuration = usages.get(type).getMinSleepDuration(timestamp, weight);
      minSleepDuration = Math.max(minSleepDuration, sleepDuration);
    }
    return minSleepDuration;
  }

  /**
   * Add the used weights to the log.
   *
   * @param weights   The weights of the new API call that has been made. Mostly the weight will
   *                  be of one specific type (such as per-IP /api weight), but in general there
   *                  could be several weights, (see, for example, New Order API call).
   * @param timestamp The time moment to consider (the `now`).
   *                  Note: this can be set so that it is easier to test.
   */
  public void addUsedWeights(ApiCallWeights weights, long timestamp) {
    for (ApiLimitType type : weights.getQuotaTypes()) {
      usages.get(type).addCall(timestamp, weights.getWeight(type));
    }
  }

  /**
   * Forget all API calls which have timed out at the given time moment.
   *
   * @param timestamp The time to consider when detecting which API calls (weights) have time out.
   */
  public void forgetOldApiCalls(long timestamp) {
    for (ApiUsage usage : usages.values()) {
      usage.forgetOldApiCalls(timestamp);
    }
  }

  /**
   * Forget all previously made API calls (their weights).
   */
  public void forgetAllApiCalls() {
    forgetOldApiCalls(Long.MAX_VALUE - 1000);
  }
}
