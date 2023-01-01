package com.binance.api.client.domain.account.dust;

import java.util.List;

/**
 * Represents details for one dust collection operation - a set of assets with small balances
 * have been converted to BNB.
 */
public class DustTransfer {
  Long operateTime;

  // Total transferred BNB amount for this exchange.
  // Warning: a spelling error here, but don't update the field name - it is misspelled in the API!
  String totalTransferedAmount;

  // Total service charge amount for this exchange.
  String totalServiceChargeAmount;
  long transId;
  List<TransferResult> userAssetDribbletDetails;

  public Long getOperateTime() {
    return operateTime;
  }

  public void setOperateTime(Long operateTime) {
    this.operateTime = operateTime;
  }

  public String getTotalTransferedAmount() {
    return totalTransferedAmount;
  }

  public void setTotalTransferedAmount(String totalTransferedAmount) {
    this.totalTransferedAmount = totalTransferedAmount;
  }

  public String getTotalServiceChargeAmount() {
    return totalServiceChargeAmount;
  }

  public void setTotalServiceChargeAmount(String totalServiceChargeAmount) {
    this.totalServiceChargeAmount = totalServiceChargeAmount;
  }

  public long getTransId() {
    return transId;
  }

  public void setTransId(long transId) {
    this.transId = transId;
  }

  public List<TransferResult> getUserAssetDribbletDetails() {
    return userAssetDribbletDetails;
  }

  public void setUserAssetDribbletDetails(List<TransferResult> userAssetDribbletDetails) {
    this.userAssetDribbletDetails = userAssetDribbletDetails;
  }

  @Override
  public String toString() {
    return "DustTransfer{" +
        "operateTime=" + operateTime +
        ", totalTransferedAmount='" + totalTransferedAmount + '\'' +
        ", totalServiceChargeAmount='" + totalServiceChargeAmount + '\'' +
        ", transId=" + transId +
        ", userAssetDribbletDetails=" + userAssetDribbletDetails +
        '}';
  }
}
