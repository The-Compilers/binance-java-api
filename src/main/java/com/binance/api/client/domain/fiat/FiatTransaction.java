package com.binance.api.client.domain.fiat;

/**
 * A single Fiat transaction: deposit or withdrawal.
 */
public class FiatTransaction {
  String orderNo;
  String fiatCurrency;
  String indicatedAmount;
  String amount;
  // Trade fee
  String totalFee;
  // Trade method: "BankAccount", "Card", etc
  String method;
  // Expired, Processing, Failed, Successful, Finished, Refunding, Refunded,
  // Refund Failed, Order Partial credit Stopped
  String status;
  long createTime;
  long updateTime;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getFiatCurrency() {
    return fiatCurrency;
  }

  public void setFiatCurrency(String fiatCurrency) {
    this.fiatCurrency = fiatCurrency;
  }

  public String getIndicatedAmount() {
    return indicatedAmount;
  }

  public void setIndicatedAmount(String indicatedAmount) {
    this.indicatedAmount = indicatedAmount;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(String totalFee) {
    this.totalFee = totalFee;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public String toString() {
    return "FiatTransaction{" +
        "orderNo='" + orderNo + '\'' +
        ", fiatCurrency='" + fiatCurrency + '\'' +
        ", indicatedAmount='" + indicatedAmount + '\'' +
        ", amount='" + amount + '\'' +
        ", totalFee='" + totalFee + '\'' +
        ", method='" + method + '\'' +
        ", status='" + status + '\'' +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        '}';
  }
}
