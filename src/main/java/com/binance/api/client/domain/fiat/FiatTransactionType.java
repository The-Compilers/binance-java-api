package com.binance.api.client.domain.fiat;

/**
 * Type of Fiat transaction, as defined in the docs.
 * https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data
 */
public enum FiatTransactionType {
  DEPOSIT("0"),
  WITHDRAW("1");

  private String value;

  FiatTransactionType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
