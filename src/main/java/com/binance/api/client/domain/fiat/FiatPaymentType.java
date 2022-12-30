package com.binance.api.client.domain.fiat;

/**
 * Type of Fiat payment, as defined in the docs.
 * https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data
 */
public enum FiatPaymentType {
  BUY("0"),
  SELL("1");

  private String value;

  FiatPaymentType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
