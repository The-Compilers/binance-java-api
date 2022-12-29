package com.binance.api.examples;

import com.binance.api.client.constant.Util;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.Trade;

import java.util.List;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExample extends AuthenticatedExampleBase {

  public static void main(String[] args) {
    AccountEndpointsExample example = new AccountEndpointsExample();
    example.run();
  }

  private void run() {
    printAccountBalances();
    printMyTrades();
    printWithdrawalHistory();
    printDepositHistory();
    // Withdraw
    // client.withdraw("ETH", "0x123", "0.1", null, null);
  }

  private void printDepositHistory() {
// TODO    System.out.println("Deposit history for LTC: ");
//    System.out.println(client.getDepositHistory("LTC"));
  }

  private void printWithdrawalHistory() {
    System.out.println("Recent Withdrawals for LTC: ");
    System.out.println(client.getWithdrawHistory("LTC"));
    System.out.println();

    String startTimeString = "2019-11-01 00:00:00";
    String endTimeString = "2019-11-30 23:59:59";
    System.out.println("Withdrawals for LTC " + startTimeString + " - " + endTimeString);
    long startTime = Util.getTimestampFor(startTimeString);
    long endTime = Util.getTimestampFor(endTimeString);
    System.out.println(client.getWithdrawHistory("LTC", null, null, null, null,
        startTime, endTime));
    System.out.println();
  }

  private void printMyTrades() {
    // Get list of trades
    List<Trade> myTrades = client.getMyTrades("BTCUSDT");
    System.out.println("My trades in the BTC/USDT market: ");
    System.out.println(myTrades);
    System.out.println("");
  }

  private void printAccountBalances() {
    Account account = client.getAccount();
    System.out.println("Balance for ETH: " + account.getAssetBalance("ETH").getTotalAmount());
    System.out.println("");

    // This would print a lot of zeroes, unreadable:
    // System.out.println("Account balances: " + account.getBalances());
  }
}
