package com.binance.api.client.domain.fiat;

import java.util.List;

/**
 * Response received from the Fiat Payment History endpoint.
 */
public class FiatPaymentHistory {
  String code;
  String message;
  int total;
  boolean success;
  List<FiatPayment> data;

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

  public List<FiatPayment> getData() {
    return data;
  }

  public void setData(List<FiatPayment> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "FiatPaymentHistory{" +
        "code='" + code + '\'' +
        ", message='" + message + '\'' +
        ", total=" + total +
        ", success=" + success +
        ", data=" + data +
        '}';
  }
}
