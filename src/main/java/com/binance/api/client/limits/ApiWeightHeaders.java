package com.binance.api.client.limits;

/**
 * Holds constants for HTTP request/response headers related to weights of individual requests and
 * currently used weights.
 */
public class ApiWeightHeaders {
  public static final String API_WEIGHT_PER_IP_KEY = "x-api-weight-ip";
  public static final String API_WEIGHT_PER_ORDER_KEY = "x-api-weight-order";
  public static final String SAPI_WEIGHT_PER_IP_KEY = "x-sapi-weight-ip";
  public static final String SAPI_WEIGHT_PER_UID_KEY = "x-sapi-weight-uid";

  public static final String API_WEIGHT_PER_IP = API_WEIGHT_PER_IP_KEY + ": ";
  public static final String API_WEIGHT_PER_ORDER = API_WEIGHT_PER_ORDER_KEY + ": ";
  public static final String SAPI_WEIGHT_PER_IP = SAPI_WEIGHT_PER_IP_KEY + ": ";
  public static final String SAPI_WEIGHT_PER_UID = SAPI_WEIGHT_PER_UID_KEY + ": ";

  public static final String API_IP_USAGE_PREFIX = "x-mbx-used-weight-";
  public static final String ORDER_COUNT_PREFIX = "x-mbx-order-count-";
  public static final String SAPI_IP_USAGE = "x-sapi-used-ip-weight-1m";
  public static final String SAPI_UID_USAGE = "x-sapi-used-uid-weight-1m";

  /**
   * No instantiation allowed.
   */
  private ApiWeightHeaders() {
  }
}
