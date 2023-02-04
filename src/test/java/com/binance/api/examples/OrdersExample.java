package com.binance.api.examples;

import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.NewOrderResponseType;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.exception.BinanceApiException;
import com.binance.api.examples.helper.AuthenticatedExampleBase;

import java.util.List;

import static com.binance.api.client.domain.account.NewOrder.limitBuy;
import static com.binance.api.client.domain.account.NewOrder.marketBuy;

/**
 * Examples on how to place orders, cancel them, and query account information.
 */
public class OrdersExample extends AuthenticatedExampleBase {

  public static void main(String[] args) {
    OrdersExample example = new OrdersExample();
    example.run();
  }

  private void run() {
    getOpenOrders();
    getAllOrders(10);
    getOrderStatus(751698L);
    cancelOrder(756762L);
    placeTestLimitOrder();
    placeTestMarketOrder();
    placeRealLimitOrder();
  }

  private void getOpenOrders() {
    List<Order> openOrders = client.getOpenOrders(new OrderRequest("BTCUSDT"));
    System.out.println("================ Open orders ================");
    System.out.println(openOrders);
    System.out.println();
  }

  private void getAllOrders(int limit) {
    System.out.println("================ All recent orders ================");
    List<Order> allOrders = client.getAllOrders(new AllOrdersRequest("BTCUSDT").limit(limit));
    System.out.println(allOrders);
    System.out.println();
  }

  private void getOrderStatus(long orderId) {
    System.out.println("================ Status for order " + orderId + "================");
    try {
      Order order = client.getOrderStatus(new OrderStatusRequest("BTCUSDT", orderId));
      System.out.println(order);
    } catch (BinanceApiException e) {
      System.out.println("Error: " + e.getError().getMsg());
    }
    System.out.println();
  }

  private void cancelOrder(long orderId) {
    try {
      System.out.println("================ Try to cancel order " + orderId + "================");
      CancelOrderResponse response = client.cancelOrder(new CancelOrderRequest("LINKETH", orderId));
      System.out.println(response);
      System.out.println();
    } catch (BinanceApiException e) {
      System.out.println(e.getError().getMsg());
    }
  }

  private void placeTestLimitOrder() {
    System.out.println("================ Test new limit-order ================");
    client.newOrderTest(limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"));
  }

  private void placeTestMarketOrder() {
    System.out.println("================ Test new market-order ================");
    client.newOrderTest(marketBuy("LINKETH", "1000"));
  }

  private void placeRealLimitOrder() {
    System.out.println("================ Place new REAL order ================");
    NewOrder order = limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001")
        .newOrderRespType(NewOrderResponseType.FULL);
    NewOrderResponse newOrderResponse = client.newOrder(order);
    System.out.println(newOrderResponse);
  }
}
