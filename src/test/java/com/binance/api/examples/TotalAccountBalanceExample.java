package com.binance.api.examples;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.constant.Util;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.security.ApiCredentials;

/**
 * Example how to get total of balances on your account
 */
public class TotalAccountBalanceExample {
  private final BinanceApiRestClient client;

  TotalAccountBalanceExample() {
    ApiCredentials credentials = ApiCredentials.creatFromEnvironment();
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(credentials);
    client = factory.newRestClient();
  }


  public static void main(String[] args) {
    TotalAccountBalanceExample example = new TotalAccountBalanceExample();
    example.run();
  }

  private void run() {
    Account account = client.getAccount();
    double totalBalanceInBtc = getTotalAccountBalanceInBTC(account);
    System.out.println("Total wallet value in BTC: " + totalBalanceInBtc);
    double balanceInUsd = totalBalanceInBtc
        * Double.parseDouble(client.getPrice("BTCUSDT").getPrice());
    System.out.println("Wallet value in USD: " + balanceInUsd);
  }

  private double getTotalAccountBalanceInBTC(Account account) {
    double totalBalance = 0;
    for (AssetBalance balance : account.getBalances()) {
      String asset = balance.getAsset();
      if (Util.isEarningsCurrency(balance.getAsset())) {
        asset = Util.earningSymbolToCoin(balance.getAsset());
      }
      if (Util.isTraded(asset)) {
        double amount;
        if (asset.equals("BTC")) {
          amount = balance.getFreeAsNumber() + balance.getLockedAsNumber();
        } else {
          amount = getAssetValueInBTC(balance, asset);
        }
        totalBalance += amount;
      }
    }
    return totalBalance;
  }

  private double getAssetValueInBTC(AssetBalance balance, String asset) {
    double free = balance.getFreeAsNumber();
    double locked = balance.getLockedAsNumber();
    double amount = 0;

    if (free + locked > 0) {
      if (Util.isFiatCurrency(asset)) {
        String tickerReverse = Util.BTC_TICKER + asset;
        amount = getFiatBtcValue(free + locked, tickerReverse);
      } else {
        String ticker = asset + Util.BTC_TICKER;
        amount = getCoinBtcValue(free + locked, ticker);
      }
    }

    return amount;
  }

  private double getCoinBtcValue(double coinAmount, String symbol) {
    System.out.println("Getting price for " + symbol);
    double coinPriceInBTC = Double.parseDouble(client.getPrice(symbol).getPrice());
    return coinPriceInBTC * coinAmount;
  }

  private double getFiatBtcValue(double fiatAmount, String symbol) {
    System.out.println("Getting price for " + symbol);
    double btcPriceInFiat = Double.parseDouble(client.getPrice(symbol).getPrice());
    return fiatAmount / btcPriceInFiat;
  }
}
