package com.binance.api.client.domain.account.dust;

import java.util.List;

/**
 * Log for dust transfer transactions.
 */
public class DustTransferLog {
  int total;
  List<DustTransfer> userAssetDribblets;

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<DustTransfer> getUserAssetDribblets() {
    return userAssetDribblets;
  }

  public void setUserAssetDribblets(List<DustTransfer> userAssetDribblets) {
    this.userAssetDribblets = userAssetDribblets;
  }

  @Override
  public String toString() {
    return "DustTransferLog{" +
        "total=" + total +
        ", userAssetDribblets=" + userAssetDribblets +
        '}';
  }
}
