package com.binance.api.client.domain.account;

/**
 * Type of Lending, as defined in the docs.
 * https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2
 */
public enum LendingType {
  DAILY("DAILY"),
  ACTIVITY("ACTIVITY"),
  CUSTOMIZED_FIXED("CUSTOMIZED_FIXED");

  private String value;

  LendingType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
