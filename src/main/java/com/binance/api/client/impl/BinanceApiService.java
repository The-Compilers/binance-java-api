package com.binance.api.client.impl;

import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.CancelOrderListResponse;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.event.ListenKey;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.ServerTime;
import com.binance.api.client.domain.market.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Binance's REST API URL mappings and endpoint security configuration.
 */
public interface BinanceApiService {

  // General endpoints

  @GET("/api/v1/ping")
  Call<Void> ping();

  @GET("/api/v1/time")
  Call<ServerTime> getServerTime();

  @GET("/api/v3/exchangeInfo")
  Call<ExchangeInfo> getExchangeInfo();

  @GET
  Call<List<Asset>> getAllAssets(@Url String url);

  // Market data endpoints

  @GET("/api/v1/depth")
  Call<OrderBook> getOrderBook(@Query("symbol") String symbol, @Query("limit") Integer limit);

  @GET("/api/v1/trades")
  Call<List<TradeHistoryItem>> getTrades(@Query("symbol") String symbol, @Query("limit") Integer limit);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
  @GET("/api/v1/historicalTrades")
  Call<List<TradeHistoryItem>> getHistoricalTrades(@Query("symbol") String symbol, @Query("limit") Integer limit, @Query("fromId") Long fromId);

  @GET("/api/v1/aggTrades")
  Call<List<AggTrade>> getAggTrades(@Query("symbol") String symbol, @Query("fromId") String fromId, @Query("limit") Integer limit,
                                    @Query("startTime") Long startTime, @Query("endTime") Long endTime);

  @GET("/api/v1/klines")
  Call<List<Candlestick>> getCandlestickBars(@Query("symbol") String symbol, @Query("interval") String interval, @Query("limit") Integer limit,
                                             @Query("startTime") Long startTime, @Query("endTime") Long endTime);

  @GET("/api/v1/ticker/24hr")
  Call<TickerStatistics> get24HrPriceStatistics(@Query("symbol") String symbol);

  @GET("/api/v1/ticker/24hr")
  Call<List<TickerStatistics>> getAll24HrPriceStatistics();

  @GET("/api/v1/ticker/allPrices")
  Call<List<TickerPrice>> getLatestPrices();

  @GET("/api/v3/ticker/price")
  Call<TickerPrice> getLatestPrice(@Query("symbol") String symbol);

  @GET("/api/v1/ticker/allBookTickers")
  Call<List<BookTicker>> getBookTickers();

  // Account endpoints

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @POST("/api/v3/order")
  Call<NewOrderResponse> newOrder(@Query("symbol") String symbol, @Query("side") OrderSide side, @Query("type") OrderType type,
                                  @Query("timeInForce") TimeInForce timeInForce, @Query("quantity") String quantity, @Query("price") String price,
                                  @Query("newClientOrderId") String newClientOrderId, @Query("stopPrice") String stopPrice,
                                  @Query("icebergQty") String icebergQty, @Query("newOrderRespType") NewOrderResponseType newOrderRespType,
                                  @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @POST("/api/v3/order")
  Call<NewOrderResponse> newOrderQuoteQty(@Query("symbol") String symbol, @Query("side") OrderSide side, @Query("type") OrderType type,
                                          @Query("timeInForce") TimeInForce timeInForce, @Query("quoteOrderQty") String quoteOrderQty, @Query("price") String price,
                                          @Query("newClientOrderId") String newClientOrderId, @Query("stopPrice") String stopPrice,
                                          @Query("icebergQty") String icebergQty, @Query("newOrderRespType") NewOrderResponseType newOrderRespType,
                                          @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @POST("/api/v3/order/test")
  Call<Void> newOrderTest(@Query("symbol") String symbol, @Query("side") OrderSide side, @Query("type") OrderType type,
                          @Query("timeInForce") TimeInForce timeInForce, @Query("quantity") String quantity, @Query("price") String price,
                          @Query("newClientOrderId") String newClientOrderId, @Query("stopPrice") String stopPrice,
                          @Query("icebergQty") String icebergQty, @Query("newOrderRespType") NewOrderResponseType newOrderRespType,
                          @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/api/v3/order")
  Call<Order> getOrderStatus(@Query("symbol") String symbol, @Query("orderId") Long orderId,
                             @Query("origClientOrderId") String origClientOrderId, @Query("recvWindow") Long recvWindow,
                             @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @DELETE("/api/v3/order")
  Call<CancelOrderResponse> cancelOrder(@Query("symbol") String symbol, @Query("orderId") Long orderId,
                                        @Query("origClientOrderId") String origClientOrderId, @Query("newClientOrderId") String newClientOrderId,
                                        @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/api/v3/openOrders")
  Call<List<Order>> getOpenOrders(@Query("symbol") String symbol, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/api/v3/allOrders")
  Call<List<Order>> getAllOrders(@Query("symbol") String symbol, @Query("orderId") Long orderId,
                                 @Query("limit") Integer limit, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @DELETE("/api/v3/orderList")
  Call<CancelOrderListResponse> cancelOrderList(@Query("symbol") String symbol, @Query("orderListId") Long orderListId, @Query("listClientOrderId") String listClientOrderId,
                                                @Query("newClientOrderId") String newClientOrderId, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/api/v3/orderList")
  Call<OrderList> getOrderListStatus(@Query("orderListId") Long orderListId, @Query("origClientOrderId") String origClientOrderId,
                                     @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/api/v3/allOrderList")
  Call<List<OrderList>> getAllOrderList(@Query("fromId") Long fromId, @Query("startTime") Long startTime, @Query("endTime") Long endTime,
                                        @Query("limit") Integer limit, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/api/v3/account")
  Call<Account> getAccount(@Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/api/v3/myTrades")
  Call<List<Trade>> getMyTrades(@Query("symbol") String symbol, @Query("limit") Integer limit, @Query("fromId") Long fromId,
                                @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @POST("/sapi/v1/capital/withdraw/apply")
  Call<WithdrawResult> withdraw(
      @Query("coin") String coin,
      @Query("withdrawOrderId") String withdrawOrderId,
      @Query("network") String network,
      @Query("address") String address,
      @Query("addressTag") String addressTag,
      @Query("amount") String amount,
      @Query("transactionFeeFlag") Boolean feeFlag,
      @Query("name") String name,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );


  /**
   * Deposit history.
   *
   * @param coin       The coin to look the history for.
   * @param status     When specified, filter by transaction status:
   *                   - 0:Email Sent
   *                   - 1:Cancelled
   *                   - 2:Awaiting Approval
   *                   - 3:Rejected
   *                   - 4:Processing
   *                   - 5:Failure
   *                   - 6:Completed
   * @param startTime  Default: 90 days from current timestamp
   * @param endTime    Default: present timestamp
   * @param offset
   * @param limit      Default: 1000, Max: 1000
   * @param recvWindow
   * @param timestamp
   * @return
   */
  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/sapi/v1/capital/deposit/hisrec")
  Call<List<Deposit>> getDepositHistory(@Query("coin") String coin, @Query("status") Integer status,
                                        @Query("startTime") Long startTime, @Query("endTime") Long endTime,
                                        @Query("offset") Integer offset, @Query("limit") Integer limit,
                                        @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  /**
   * Withdraw History.
   * See https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data
   * Comments from the docs:
   * Please notice the default startTime and endTime to make sure that time interval is within 0-90 days.
   * If both startTime and endTime are sent, time between startTime and endTime must be less than 90 days.
   * If withdrawOrderId is sent, time between startTime and endTime must be less than 7 days.
   * If withdrawOrderId is sent, startTime and endTime are not sent, will return last 7 days records by default.
   *
   * @param coin
   * @param withdrawOrderId
   * @param status          When specified, filter by transaction status:
   *                        - 0:Email Sent
   *                        - 1:Cancelled
   *                        - 2:Awaiting Approval
   *                        - 3:Rejected
   *                        - 4:Processing
   *                        - 5:Failure
   *                        - 6:Completed
   * @param offset
   * @param limit           Default: 1000, Max: 1000
   * @param startTime       Default: 90 days from current timestamp
   * @param endTime         Default: present timestamp
   * @param recvWindow
   * @param timestamp
   * @return HTTP Call for the API endpoint
   */
  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/sapi/v1/capital/withdraw/history")
  Call<List<Withdraw>> getWithdrawHistory(
      @Query("coin") String coin,
      @Query("withdrawOrderId") String withdrawOrderId,
      @Query("status") Integer status,
      @Query("offset") Integer offset,
      @Query("limit") Integer limit,
      @Query("startTime") Long startTime,
      @Query("endTime") Long endTime,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @GET("/sapi/v1/capital/deposit/address")
  Call<DepositAddress> getDepositAddress(@Query("coin") String asset, @Query("network")
  String network, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @POST("/sapi/v1/asset/dust")
  Call<DustTransferResponse> dustTransfer(@Query("asset") List<String> asset, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
  @POST("/sapi/v3/asset/getUserAsset")
  Call<List<ExtendedAssetBalance>> getUserAssets(
      @Query("asset") String asset,
      @Query("needBtcValuation") boolean needBtcValuation,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  // User stream endpoints

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
  @POST("/api/v1/userDataStream")
  Call<ListenKey> startUserDataStream();

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
  @PUT("/api/v1/userDataStream")
  Call<Void> keepAliveUserDataStream(@Query("listenKey") String listenKey);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
  @DELETE("/api/v1/userDataStream")
  Call<Void> closeAliveUserDataStream(@Query("listenKey") String listenKey);

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
  @POST("/sapi/v1/userDataStream")
  Call<ListenKey> startMarginUserDataStream();

  @Headers(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY_HEADER)
  @PUT("/sapi/v1/userDataStream")
  Call<Void> keepAliveMarginUserDataStream(@Query("listenKey") String listenKey);

}
