package com.binance.api.client.domain.fiat;

/**
 * Represents a payment with Fiat currency.
 */
public class FiatPayment {
  String orderNo;
  // Fiat trade amount
  String sourceAmount;
  // Fiat token ("EUR", etc)
  String fiatCurrency;
  // Crypto trade amount
  String obtainAmount;
  // Crypto token
  String cryptoCurrency;
  // Trade fee (in the fiat currency)
  String totalFee;
  String price;
  // "Processing", "Completed", "Failed", "Refunded"
  String status;
  // Contained only for buy-payments (deposits). Following methods currently supported (2022-12):
  // "Credit Card", "Cash Balance", "Online Banking", "Bank Transfer"
  String paymentMethod;
  long createTime;
  long updateTime;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getSourceAmount() {
    return sourceAmount;
  }

  public void setSourceAmount(String sourceAmount) {
    this.sourceAmount = sourceAmount;
  }

  public String getFiatCurrency() {
    return fiatCurrency;
  }

  public void setFiatCurrency(String fiatCurrency) {
    this.fiatCurrency = fiatCurrency;
  }

  public String getObtainAmount() {
    return obtainAmount;
  }

  public void setObtainAmount(String obtainAmount) {
    this.obtainAmount = obtainAmount;
  }

  public String getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(String totalFee) {
    this.totalFee = totalFee;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
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

  public String getCryptoCurrency() {
    return cryptoCurrency;
  }

  public void setCryptoCurrency(String cryptoCurrency) {
    this.cryptoCurrency = cryptoCurrency;
  }

  @Override
  public String toString() {
    return "FiatPayment{" +
        "orderNo='" + orderNo + '\'' +
        ", sourceAmount='" + sourceAmount + '\'' +
        ", fiatCurrency='" + fiatCurrency + '\'' +
        ", obtainAmount='" + obtainAmount + '\'' +
        ", cryptoCurrency='" + cryptoCurrency + '\'' +
        ", totalFee='" + totalFee + '\'' +
        ", price='" + price + '\'' +
        ", status='" + status + '\'' +
        ", paymentMethod='" + paymentMethod + '\'' +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        '}';
  }
}
