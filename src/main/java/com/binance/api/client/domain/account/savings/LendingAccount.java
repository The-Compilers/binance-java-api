package com.binance.api.client.domain.account.savings;

/**
 * Lending account summary for one asset.
 */
public class LendingAccount {
  String amount;
  String amountInBTC;
  String amountInUSDT;
  String asset;

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getAmountInBTC() {
    return amountInBTC;
  }

  public void setAmountInBTC(String amountInBTC) {
    this.amountInBTC = amountInBTC;
  }

  public String getAmountInUSDT() {
    return amountInUSDT;
  }

  public void setAmountInUSDT(String amountInUSDT) {
    this.amountInUSDT = amountInUSDT;
  }

  public String getAsset() {
    return asset;
  }

  public void setAsset(String asset) {
    this.asset = asset;
  }

  @Override
  public String toString() {
    return "LendingAccount{" +
        "amount='" + amount + '\'' +
        ", amountInBTC='" + amountInBTC + '\'' +
        ", amountInUSDT='" + amountInUSDT + '\'' +
        ", asset='" + asset + '\'' +
        '}';
  }
}
