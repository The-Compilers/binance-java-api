package com.binance.api.client.domain.account;

import java.util.List;

/**
 * Response for Asset Dividend record request.
 */
public class AssetDividendHistory {
  int total;
  List<AssetDividend> rows;

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<AssetDividend> getRows() {
    return rows;
  }

  public void setRows(List<AssetDividend> rows) {
    this.rows = rows;
  }

  @Override
  public String toString() {
    return "AssetDividendHistory{" +
        "total=" + total +
        ", rows=" + rows +
        '}';
  }
}
