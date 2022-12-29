package com.binance.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Asset balance with extra information. Used in some endpoints, for example
 * User Asset: https://binance-docs.github.io/apidocs/spot/en/#user-asset-user_data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendedAssetBalance extends AssetBalance {
  private String freeze;
  private String withdrawing;
  private String ipoable;
  private String btcValuation;

  public String getFreeze() {
    return freeze;
  }

  public void setFreeze(String freeze) {
    this.freeze = freeze;
  }

  public String getWithdrawing() {
    return withdrawing;
  }

  public void setWithdrawing(String withdrawing) {
    this.withdrawing = withdrawing;
  }

  public String getIpoable() {
    return ipoable;
  }

  public void setIpoable(String ipoable) {
    this.ipoable = ipoable;
  }

  /**
   * Get the asset value in BTC, according to current prices.
   *
   * @return BTC-value of the asset
   */
  public String getBtcValuation() {
    return btcValuation;
  }

  public void setBtcValuation(String btcValuation) {
    this.btcValuation = btcValuation;
  }

  /**
   * Get the BTC value, as a floating point number.
   *
   * @return The BTC value of the asset
   * @throws NumberFormatException when the value is not a number
   */
  public double getBtcValuationAsNumber() {
    return Double.parseDouble(btcValuation);
  }
}
