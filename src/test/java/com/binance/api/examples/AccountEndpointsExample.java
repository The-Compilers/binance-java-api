package com.binance.api.examples;

import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.Trade;
import org.apache.log4j.PropertyConfigurator;

import java.util.List;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExample extends AuthenticatedExampleBase {

  public static void main(String[] args) {
    PropertyConfigurator.configure("log4j.properties");
    AccountEndpointsExample example = new AccountEndpointsExample();
    example.run();
  }

  private void run() {
    printAccountBalances();
    printMyTrades();
  }

  private void printMyTrades() {
    printTradesInMarket("BTCUSDT");
    printTradesInMarket("LTCUSDT");
    printTradesInMarket("ETHUSDT");
  }

  private void printTradesInMarket(String symbol) {
    List<Trade> myTrades = client.getMyTrades(symbol);
    System.out.println("My trades in the " + symbol + " market: ");
    System.out.println(myTrades);
    System.out.println();
  }

  private void printAccountBalances() {
    Account account = client.getAccount();
    System.out.println("Balance for ETH: " + account.getAssetBalance("ETH").getTotalAmount());
    System.out.println();

    // This would print a lot of zeroes, unreadable:
    // System.out.println("Account balances: " + account.getBalances());
  }
}
