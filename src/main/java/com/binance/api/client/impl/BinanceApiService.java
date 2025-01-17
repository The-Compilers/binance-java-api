package com.binance.api.client.impl;

import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.dust.DustTransferLog;
import com.binance.api.client.domain.account.dust.DustTransferResponse;
import com.binance.api.client.domain.account.request.CancelOrderListResponse;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.savings.LendingAccountSummary;
import com.binance.api.client.domain.account.savings.SavingsInterest;
import com.binance.api.client.domain.event.ListenKey;
import com.binance.api.client.domain.fiat.FiatPaymentHistory;
import com.binance.api.client.domain.fiat.FiatTransactionHistory;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.ServerTime;
import com.binance.api.client.domain.market.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import static com.binance.api.client.limits.ApiWeightHeaders.*;

/**
 * Binance's REST API URL mappings and endpoint security configuration.
 */
public interface BinanceApiService {

  // General endpoints

  @Headers(API_WEIGHT_PER_IP + 1)
  @GET("/api/v1/ping")
  Call<Void> ping();

  @Headers(API_WEIGHT_PER_IP + 1)
  @GET("/api/v1/time")
  Call<ServerTime> getServerTime();

  @Headers(API_WEIGHT_PER_IP + 10)
  @GET("/api/v3/exchangeInfo")
  Call<ExchangeInfo> getExchangeInfo();


  @Headers(API_WEIGHT_PER_IP + 50)
  @GET("/api/v3/depth")
  Call<OrderBook> getOrderBook(@Query("symbol") String symbol, @Query("limit") Integer limit);

  // Market data endpoints

  @Headers(API_WEIGHT_PER_IP + 1)
  @GET("/api/v3/trades")
  Call<List<TradeHistoryItem>> getTrades(@Query("symbol") String symbol, @Query("limit") Integer limit);

  @Headers(API_WEIGHT_PER_IP + 5)
  @GET("/api/v3/historicalTrades")
  Call<List<TradeHistoryItem>> getHistoricalTrades(@Query("symbol") String symbol, @Query("limit") Integer limit, @Query("fromId") Long fromId);

  @Headers(API_WEIGHT_PER_IP + 1)
  @GET("/api/v3/aggTrades")
  Call<List<AggTrade>> getAggTrades(@Query("symbol") String symbol, @Query("fromId") String fromId, @Query("limit") Integer limit,
                                    @Query("startTime") Long startTime, @Query("endTime") Long endTime);

  @Headers(API_WEIGHT_PER_IP + 1)
  @GET("/api/v3/klines")
  Call<List<Candlestick>> getCandlestickBars(@Query("symbol") String symbol, @Query("interval") String interval, @Query("limit") Integer limit,
                                             @Query("startTime") Long startTime, @Query("endTime") Long endTime);

  @Headers(API_WEIGHT_PER_IP + 40)
  @GET("/api/v3/ticker/24hr")
  Call<TickerStatistics> get24HrPriceStatistics(@Query("symbol") String symbol);

  @Headers(API_WEIGHT_PER_IP + 40)
  @GET("/api/v3/ticker/24hr")
  Call<List<TickerStatistics>> getAll24HrPriceStatistics();

  @Headers(API_WEIGHT_PER_IP + 2)
  @GET("/api/v3/ticker/price")
  Call<TickerPrice> getLatestPrice(@Query("symbol") String symbol);

  // Account endpoints

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 1, API_WEIGHT_PER_ORDER + 1})
  @POST("/api/v3/order")
  Call<NewOrderResponse> newOrder(@Query("symbol") String symbol, @Query("side") OrderSide side, @Query("type") OrderType type,
                                  @Query("timeInForce") TimeInForce timeInForce, @Query("quantity") String quantity, @Query("price") String price,
                                  @Query("newClientOrderId") String newClientOrderId, @Query("stopPrice") String stopPrice,
                                  @Query("icebergQty") String icebergQty, @Query("newOrderRespType") NewOrderResponseType newOrderRespType,
                                  @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 1, API_WEIGHT_PER_ORDER + 1})
  @POST("/api/v3/order")
  Call<NewOrderResponse> newOrderQuoteQty(@Query("symbol") String symbol, @Query("side") OrderSide side, @Query("type") OrderType type,
                                          @Query("timeInForce") TimeInForce timeInForce, @Query("quoteOrderQty") String quoteOrderQty, @Query("price") String price,
                                          @Query("newClientOrderId") String newClientOrderId, @Query("stopPrice") String stopPrice,
                                          @Query("icebergQty") String icebergQty, @Query("newOrderRespType") NewOrderResponseType newOrderRespType,
                                          @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 1, API_WEIGHT_PER_ORDER + 1})
  @POST("/api/v3/order/test")
  Call<Void> newOrderTest(@Query("symbol") String symbol, @Query("side") OrderSide side, @Query("type") OrderType type,
                          @Query("timeInForce") TimeInForce timeInForce, @Query("quantity") String quantity, @Query("price") String price,
                          @Query("newClientOrderId") String newClientOrderId, @Query("stopPrice") String stopPrice,
                          @Query("icebergQty") String icebergQty, @Query("newOrderRespType") NewOrderResponseType newOrderRespType,
                          @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 2})
  @GET("/api/v3/order")
  Call<Order> getOrderStatus(@Query("symbol") String symbol, @Query("orderId") Long orderId,
                             @Query("origClientOrderId") String origClientOrderId, @Query("recvWindow") Long recvWindow,
                             @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 1})
  @DELETE("/api/v3/order")
  Call<CancelOrderResponse> cancelOrder(@Query("symbol") String symbol, @Query("orderId") Long orderId,
                                        @Query("origClientOrderId") String origClientOrderId, @Query("newClientOrderId") String newClientOrderId,
                                        @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 40})
  @GET("/api/v3/openOrders")
  Call<List<Order>> getOpenOrders(@Query("symbol") String symbol, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 10})
  @GET("/api/v3/allOrders")
  Call<List<Order>> getAllOrders(
      @Query("symbol") String symbol,
      @Query("orderId") Long orderId,
      @Query("startTime") Long startTime,
      @Query("endTime") Long endTime,
      @Query("limit") Integer limit,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 1})
  @DELETE("/api/v3/orderList")
  Call<CancelOrderListResponse> cancelOrderList(@Query("symbol") String symbol, @Query("orderListId") Long orderListId, @Query("listClientOrderId") String listClientOrderId,
                                                @Query("newClientOrderId") String newClientOrderId, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 2})
  @GET("/api/v3/orderList")
  Call<OrderList> getOrderListStatus(@Query("orderListId") Long orderListId, @Query("origClientOrderId") String origClientOrderId,
                                     @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 10})
  @GET("/api/v3/allOrderList")
  Call<List<OrderList>> getAllOrderList(@Query("fromId") Long fromId, @Query("startTime") Long startTime, @Query("endTime") Long endTime,
                                        @Query("limit") Integer limit, @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 10})
  @GET("/api/v3/account")
  Call<Account> getAccount(@Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, API_WEIGHT_PER_IP + 10})
  @GET("/api/v3/myTrades")
  Call<List<Trade>> getMyTrades(@Query("symbol") String symbol, @Query("limit") Integer limit, @Query("fromId") Long fromId,
                                @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_UID + 600})
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
  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
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
  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
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
      @Query("timestamp") Long timestamp
  );

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 10})
  @GET("/sapi/v1/capital/deposit/address")
  Call<DepositAddress> getDepositAddress(
      @Query("coin") String asset, @Query("network") String network,
      @Query("recvWindow") Long recvWindow, @Query("timestamp") Long timestamp
  );

  /**
   * Get Dust transfer log.
   * See https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data
   *
   * @param startTime  When specified, return only records with time >= startTime
   * @param endTime    When specified, return only records with time <= endTime
   * @param recvWindow Receive window, in milliseconds
   * @param timestamp  The current system timestamp
   * @return Log of dust transfers
   */
  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @GET("/sapi/v1/asset/dribblet")
  Call<DustTransferLog> getDustLog(
      @Query("startTime") Long startTime,
      @Query("endTime") Long endTime,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_UID + 10})
  @POST("/sapi/v1/asset/dust")
  Call<DustTransferResponse> dustTransfer(
      @Query("asset") List<String> asset,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 5})
  @POST("/sapi/v3/asset/getUserAsset")
  Call<List<ExtendedAssetBalance>> getUserAssets(
      @Query("asset") String asset,
      @Query("needBtcValuation") boolean needBtcValuation,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 10})
  @GET("/sapi/v1/asset/assetDividend")
  Call<AssetDividendHistory> getAssetDividendRecord(
      @Query("asset") String asset,
      @Query("startTime") Long beginTime,
      @Query("endTime") Long endTime,
      @Query("limit") Integer limit, // Default 20, max 500
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  // User stream endpoints

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @POST("/api/v3/userDataStream")
  Call<ListenKey> startUserDataStream();

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @PUT("/api/v3/userDataStream")
  Call<Void> keepAliveUserDataStream(@Query("listenKey") String listenKey);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @DELETE("/api/v3/userDataStream")
  Call<Void> closeAliveUserDataStream(@Query("listenKey") String listenKey);

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @POST("/sapi/v1/userDataStream")
  Call<ListenKey> startMarginUserDataStream();

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @PUT("/sapi/v1/userDataStream")
  Call<Void> keepAliveMarginUserDataStream(@Query("listenKey") String listenKey);

  // Fiat endpoints
  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_UID + 90000})
  @GET("/sapi/v1/fiat/orders")
  Call<FiatTransactionHistory> getFiatDepositOrWithdrawalHistory(
      @Query("transactionType") String transactionType, // 0: deposit; 1: withdrawal
      @Query("beginTime") Long beginTime,
      @Query("endTime") Long endTime,
      @Query("page") Integer page, // default 1
      @Query("rows") Integer rows, // default 100, max 500
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @GET("/sapi/v1/fiat/payments")
  Call<FiatPaymentHistory> getFiatPaymentHistory(
      @Query("transactionType") String transactionType, // 0: buy; 1: sell
      @Query("beginTime") Long beginTime,
      @Query("endTime") Long endTime,
      @Query("page") Integer page, // default 1
      @Query("rows") Integer rows, // default 100, max 500
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  // Savings endpoints

  /**
   * Get savings interest history for the user's account.
   *
   * @param lendingType "DAILY" for flexible, "ACTIVITY" for activity, "CUSTOMIZED_FIXED" for fixed
   * @param asset       The asset which was put in the savings account (BTC, etc.)
   * @param beginTime   Minimum timestamp for filtering the results
   * @param endTime     Maximum timestamp for filtering the results The time between startTime
   *                    and endTime cannot be longer than 30 days.
   *                    If startTime and endTime are both not sent, then the last 30 days'
   *                    data will be returned.
   * @param page        Current querying page. Start from 1. Default: 1
   * @param size        Items per "page", Default:10, Max:100
   * @param recvWindow
   * @param timestamp
   * @return List of SavingsInterest objects
   */
  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @GET("/sapi/v1/lending/union/interestHistory")
  Call<List<SavingsInterest>> getSavingsInterestHistory(
      @Query("lendingType") String lendingType,
      @Query("asset") String asset,
      @Query("startTime") Long beginTime,
      @Query("endTime") Long endTime,
      @Query("current") Long page,
      @Query("size") Long size,
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );

  @Headers({BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER, SAPI_WEIGHT_PER_IP + 1})
  @GET("/sapi/v1/lending/union/account")
  Call<LendingAccountSummary> getLendingAccount(
      @Query("recvWindow") Long recvWindow,
      @Query("timestamp") Long timestamp
  );
}
