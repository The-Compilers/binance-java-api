package com.binance.api.examples;

import com.binance.api.client.constant.Util;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.examples.helper.AuthenticatedExampleBase;
import org.apache.log4j.PropertyConfigurator;
import java.util.List;
import static com.binance.api.client.constant.Util.humanReadableTimestamp;

/**
 * Try to send many requests, check if the limits are working properly (we should not get a ban).
 */
public class ApiLimitExample extends AuthenticatedExampleBase {
  public static void main(String[] args) {
    PropertyConfigurator.configure("log4j.properties");
    ApiLimitExample example = new ApiLimitExample();
    example.run();
  }

  private void run() {
    // These should go without any sleep
    getRecentWithdrawals();
    getRecentDeposits();

    // Here you might get a sleep
    getManyOrders();

    // If the orders required a sleep, this should go without a sleep again
    getRecentWithdrawals();
    getRecentDeposits();
  }

  private void getRecentDeposits() {
    System.out.println("Getting recent deposits...");
    client.getRecentFiatDepositHistory();
  }

  private void getRecentWithdrawals() {
    System.out.println("Getting recent withdrawals...");
    client.getRecentFiatWithdrawHistory();

  }

  private void getManyOrders() {
    String[] symbols = new String[]{"BTCUSDT", "LTCUSDT", "ETHUSDT", "BNBUSDT", "LUNAUSDT"};
    for (String symbol : symbols) {
      getOrderHistory(symbol);
    }
  }

  private void getOrderHistory(String symbol) {
    long exchangeStartTime = Util.getTimestampFor("2019-08-01 00:00:00");
    AllOrdersRequest request = new AllOrdersRequest(symbol).startTime(exchangeStartTime);
    List<Order> orders = client.getAllOrders(request);
    while (!orders.isEmpty()) {
      Order lastOrder = orders.get(orders.size() - 1);
      System.out.println(orders.size() + " orders found");
      String orderTime = humanReadableTimestamp(lastOrder.getTime());
      System.out.println("Last order (" + orderTime + "): " + lastOrder);
      request.orderId(lastOrder.getOrderId() + 1).startTime(null);
      orders = client.getAllOrders(request);
    }
  }

}
