package com.binance.api.client.limits;

/**
 * One registered Api call - the time of it and used weight.
 */
public class ApiCallEntry {
  private long time;
  private int weight;

  /**
   * Create a new entry documenting an API call - when the call was made and what was it's weight.
   *
   * @param time   The time of the call, Unix timestamp with milliseconds
   * @param weight The weight of the call
   */
  public ApiCallEntry(long time, int weight) {
    this.time = time;
    this.weight = weight;
  }

  /**
   * Get the time when the call was made.
   *
   * @return The time of the call, Unix timestamp with milliseconds
   */
  public long getTime() {
    return time;
  }

  /**
   * Get the weight of the call.
   *
   * @return The weight of the call
   */
  public int getWeight() {
    return weight;
  }
}
