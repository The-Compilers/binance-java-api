package com.binance.api.client.domain.account;

import com.binance.api.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A withdrawal from a Binance account.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Withdraw {

  /**
   * Amount withdrawn.
   */
  private String amount;

  /**
   * Destination address.
   */
  private String address;

  /**
   * Symbol.
   */
  private String coin;

  private String applyTime;

  /**
   * Transaction id.
   */
  private String txId;

  /**
   * Id.
   */
  private String id;

  /**
   * (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6:Completed)
   */
  private int status;

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCoin() {
    return coin;
  }

  public void setCoin(String coin) {
    this.coin = coin;
  }

  public String getApplyTime() {
    return applyTime;
  }

  public void setApplyTime(String applyTime) {
    this.applyTime = applyTime;
  }

  public String getTxId() {
    return txId;
  }

  public void setTxId(String txId) {
    this.txId = txId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
        .append("amount", amount)
        .append("address", address)
        .append("coin", coin)
        .append("applyTime", applyTime)
        .append("txId", txId)
        .append("id", id)
        .append("status", status)
        .toString();
  }
}
