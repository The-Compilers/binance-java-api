package com.binance.api.client.domain.account.savings;

import java.util.List;

/**
 * Lending account summary for savings and auto-invest.
 */
public class LendingAccountSummary {
  String totalAmountInBTC;
  String totalAmountInUSDT;
  String totalFixedAmountInBTC;
  String totalFixedAmountInUSDT;
  String totalFlexibleInBTC;
  String totalFlexibleInUSDT;
  List<LendingAccount> positionAmountVos;

  public String getTotalAmountInBTC() {
    return totalAmountInBTC;
  }

  public void setTotalAmountInBTC(String totalAmountInBTC) {
    this.totalAmountInBTC = totalAmountInBTC;
  }

  public String getTotalAmountInUSDT() {
    return totalAmountInUSDT;
  }

  public void setTotalAmountInUSDT(String totalAmountInUSDT) {
    this.totalAmountInUSDT = totalAmountInUSDT;
  }

  public String getTotalFixedAmountInBTC() {
    return totalFixedAmountInBTC;
  }

  public void setTotalFixedAmountInBTC(String totalFixedAmountInBTC) {
    this.totalFixedAmountInBTC = totalFixedAmountInBTC;
  }

  public String getTotalFixedAmountInUSDT() {
    return totalFixedAmountInUSDT;
  }

  public void setTotalFixedAmountInUSDT(String totalFixedAmountInUSDT) {
    this.totalFixedAmountInUSDT = totalFixedAmountInUSDT;
  }

  public String getTotalFlexibleInBTC() {
    return totalFlexibleInBTC;
  }

  public void setTotalFlexibleInBTC(String totalFlexibleInBTC) {
    this.totalFlexibleInBTC = totalFlexibleInBTC;
  }

  public String getTotalFlexibleInUSDT() {
    return totalFlexibleInUSDT;
  }

  public void setTotalFlexibleInUSDT(String totalFlexibleInUSDT) {
    this.totalFlexibleInUSDT = totalFlexibleInUSDT;
  }

  public List<LendingAccount> getPositionAmountVos() {
    return positionAmountVos;
  }

  public void setPositionAmountVos(List<LendingAccount> positionAmountVos) {
    this.positionAmountVos = positionAmountVos;
  }

  @Override
  public String toString() {
    return "LendingAccountSummary{" +
        "totalAmountInBTC='" + totalAmountInBTC + '\'' +
        ", totalAmountInUSDT='" + totalAmountInUSDT + '\'' +
        ", totalFixedAmountInBTC='" + totalFixedAmountInBTC + '\'' +
        ", totalFixedAmountInUSDT='" + totalFixedAmountInUSDT + '\'' +
        ", totalFlexibleInBTC='" + totalFlexibleInBTC + '\'' +
        ", totalFlexibleInUSDT='" + totalFlexibleInUSDT + '\'' +
        ", positionAmountVos=" + positionAmountVos +
        '}';
  }
}
