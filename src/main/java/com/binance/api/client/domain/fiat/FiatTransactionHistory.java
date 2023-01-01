package com.binance.api.client.domain.fiat;

import java.util.List;

/**
 * Represents a history of FIAT transactions (deposits or withdrawals).
 */
public class FiatTransactionHistory {
  String code;
  String message;
  int total;
  boolean success;
  List<FiatTransaction> data;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public List<FiatTransaction> getData() {
    return data;
  }

  public void setData(List<FiatTransaction> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "FiatTransactionHistory{" +
        "code='" + code + '\'' +
        ", message='" + message + '\'' +
        ", total=" + total +
        ", success=" + success +
        ", data=" + data +
        '}';
  }
}
