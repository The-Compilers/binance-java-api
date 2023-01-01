package com.binance.api.client.domain.account;

/**
 * Asset dividend record.
 */
public class AssetDividend {
  long id;
  String amount;
  String asset;
  long divTime;
  String enInfo;
  long tranId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getAsset() {
    return asset;
  }

  public void setAsset(String asset) {
    this.asset = asset;
  }

  public long getDivTime() {
    return divTime;
  }

  public void setDivTime(long divTime) {
    this.divTime = divTime;
  }

  public String getEnInfo() {
    return enInfo;
  }

  public void setEnInfo(String enInfo) {
    this.enInfo = enInfo;
  }

  public long getTranId() {
    return tranId;
  }

  public void setTranId(long tranId) {
    this.tranId = tranId;
  }

  @Override
  public String toString() {
    return "AssetDividend{" +
        "id=" + id +
        ", amount='" + amount + '\'' +
        ", asset='" + asset + '\'' +
        ", divTime=" + divTime +
        ", enInfo='" + enInfo + '\'' +
        ", tranId=" + tranId +
        '}';
  }
}
