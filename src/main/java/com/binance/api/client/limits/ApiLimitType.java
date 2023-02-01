package com.binance.api.client.limits;

/**
 * The different types of API limitations: general API limit Per IP,
 * private account (SAPI) limit per IP, per user (UID) limit, and order-related.
 */
enum ApiLimitType {
  ApiIp, SapiIp, SapiUid, Order;
}
