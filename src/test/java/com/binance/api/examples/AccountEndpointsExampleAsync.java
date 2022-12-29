package com.binance.api.examples;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.account.Account;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExampleAsync extends AuthenticatedAsyncExampleBase {

  public static void main(String[] args) {
    AccountEndpointsExampleAsync example = new AccountEndpointsExampleAsync();
    example.run();
  }

  private void run() {
    // Get account balances (async)
    client.getAccount((Account response) -> System.out.println(
        "ETH balance: " + response.getAssetBalance("ETH").getTotalAmount())
    );

    // Get list of trades (async)
    client.getMyTrades("NEOETH", response -> System.out.println("Trades: " + response));

    // Get withdraw history (async)
    client.getWithdrawHistory("ETH", response -> System.out.println(
        "Withdrawal history: " + response));

    // Get deposit history (async)
    client.getDepositHistory("ETH", response -> System.out.println("Deposit history: " + response));

    // Withdraw (async)
    // client.withdraw("ETH", "0x123", "0.1", null, null, null, null, null, response -> {});
  }
}
