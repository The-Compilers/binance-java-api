package com.binance.api.client.limits;

import java.util.HashMap;
import java.util.Map;

/**
 * Weights of a specific API call.
 */
public class ApiCallWeights {
  private final Map<ApiLimitType, Integer> weights = new HashMap<>();

  /**
   * Set a weight for a request, in a specific quota.
   *
   * @param quotaType The type of the quota
   * @param weight    Weight of this request in terms of the specified quota
   * @return This object, for chained method calls
   */
  public ApiCallWeights setWeight(ApiLimitType quotaType, Integer weight) {
    if (weight != null) {
      weights.put(quotaType, weight);
    }
    return this;
  }

  /**
   * Get the weight for a specific quota type.
   *
   * @param quotaType The type of the quota to check
   * @return Weight for the specified quota type or null if no weight exists for it
   */
  public Integer getWeight(ApiLimitType quotaType) {
    return weights.get(quotaType);
  }

  /**
   * Get all the quota types registered in these weights.
   *
   * @return The quota types as an iterable collection
   */
  public Iterable<ApiLimitType> getQuotaTypes() {
    return weights.keySet();
  }
}
