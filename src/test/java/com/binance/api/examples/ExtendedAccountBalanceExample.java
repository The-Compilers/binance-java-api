package com.binance.api.examples;

import com.binance.api.client.domain.account.ExtendedAssetBalance;
import java.util.List;

/**
 * List the current user assets in the wallet, and their BTC value
 */
public class ExtendedAccountBalanceExample extends AuthenticatedExampleBase {


  public static void main(String[] args) {
    ExtendedAccountBalanceExample example = new ExtendedAccountBalanceExample();
    example.run();
  }

  private void run() {
    List<ExtendedAssetBalance> balances = client.getUserAssets(null, true);
    double totalBtcValue = 0;
    for (ExtendedAssetBalance balance : balances) {
      System.out.println(balance.getAsset() + ": " + balance.getTotalAmount() + " == "
          + balance.getBtcValuation() + " BTC");
      totalBtcValue += balance.getBtcValuationAsNumber();
    }
    System.out.println("Total BTC value of the wallet (Spot): " + totalBtcValue);
  }
}
