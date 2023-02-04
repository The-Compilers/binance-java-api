package com.binance.api.client.limits;

/**
 * The different types of API limitations: general API limit Per IP,
 * private account (SAPI) limit per IP, per user (UID) limit, and order-related.
 */
enum ApiLimitType {
  API_IP, SAPI_IP, SAPI_UID, ORDER;
}
