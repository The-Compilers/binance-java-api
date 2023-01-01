package com.binance.api.client.domain.account.savings;

/**
 * Interest on savings.
 */
public class SavingsInterest {
  String asset; // BTC, USDT, etc
  String interest; // Interest amount
  String lendingType; // DAILY, etc
  String productName;
  long time; // Timestamp of the moment when the interest was paid, including milliseconds

  public String getAsset() {
    return asset;
  }

  public void setAsset(String asset) {
    this.asset = asset;
  }

  public String getInterest() {
    return interest;
  }

  public void setInterest(String interest) {
    this.interest = interest;
  }

  public String getLendingType() {
    return lendingType;
  }

  public void setLendingType(String lendingType) {
    this.lendingType = lendingType;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "SavingsInterest{" +
        "asset='" + asset + '\'' +
        ", interest='" + interest + '\'' +
        ", lendingType='" + lendingType + '\'' +
        ", productName='" + productName + '\'' +
        ", time=" + time +
        '}';
  }
}
