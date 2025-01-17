package com.binance.api.client.impl;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.dust.DustTransferLog;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.CancelOrderResponse;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.account.savings.LendingAccountSummary;
import com.binance.api.client.domain.account.savings.LendingType;
import com.binance.api.client.domain.account.savings.SavingsInterest;
import com.binance.api.client.domain.event.ListenKey;
import com.binance.api.client.domain.fiat.FiatPaymentHistory;
import com.binance.api.client.domain.fiat.FiatPaymentType;
import com.binance.api.client.domain.fiat.FiatTransactionHistory;
import com.binance.api.client.domain.fiat.FiatTransactionType;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.ServerTime;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;
import retrofit2.Call;

import java.util.List;

import static com.binance.api.client.impl.BinanceApiServiceGenerator.createService;

/**
 * Implementation of Binance's REST API using Retrofit with asynchronous/non-blocking method calls.
 */
public class BinanceApiAsyncRestClientImpl implements BinanceApiAsyncRestClient {

  private final BinanceApiService binanceApiService;

  public BinanceApiAsyncRestClientImpl(String apiKey, String secret) {
    binanceApiService = createService(BinanceApiService.class, apiKey, secret);
  }

  // General endpoints

  @Override
  public void ping(BinanceApiCallback<Void> callback) {
    binanceApiService.ping().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getServerTime(BinanceApiCallback<ServerTime> callback) {
    binanceApiService.getServerTime().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getExchangeInfo(BinanceApiCallback<ExchangeInfo> callback) {
    binanceApiService.getExchangeInfo().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  // Market Data endpoints

  @Override
  public void getOrderBook(String symbol, Integer limit, BinanceApiCallback<OrderBook> callback) {
    binanceApiService.getOrderBook(symbol, limit).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getTrades(String symbol, Integer limit, BinanceApiCallback<List<TradeHistoryItem>> callback) {
    binanceApiService.getTrades(symbol, limit).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getHistoricalTrades(String symbol, Integer limit, Long fromId, BinanceApiCallback<List<TradeHistoryItem>> callback) {
    binanceApiService.getHistoricalTrades(symbol, limit, fromId).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<AggTrade>> callback) {
    binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAggTrades(String symbol, BinanceApiCallback<List<AggTrade>> callback) {
    getAggTrades(symbol, null, null, null, null, callback);
  }

  @Override
  public void getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<Candlestick>> callback) {
    binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getCandlestickBars(String symbol, CandlestickInterval interval, BinanceApiCallback<List<Candlestick>> callback) {
    getCandlestickBars(symbol, interval, null, null, null, callback);
  }

  @Override
  public void get24HrPriceStatistics(String symbol, BinanceApiCallback<TickerStatistics> callback) {
    binanceApiService.get24HrPriceStatistics(symbol).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAll24HrPriceStatistics(BinanceApiCallback<List<TickerStatistics>> callback) {
    binanceApiService.getAll24HrPriceStatistics().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getPrice(String symbol, BinanceApiCallback<TickerPrice> callback) {
    binanceApiService.getLatestPrice(symbol).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void newOrder(NewOrder order, BinanceApiCallback<NewOrderResponse> callback) {
    if (order.getQuoteOrderQty() == null) {
      binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
          order.getTimeInForce(), order.getQuantity(), order.getPrice(),
          order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(),
          order.getRecvWindow(), order.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
    } else {
      binanceApiService.newOrderQuoteQty(order.getSymbol(), order.getSide(), order.getType(),
          order.getTimeInForce(), order.getQuoteOrderQty(), order.getPrice(),
          order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(),
          order.getRecvWindow(), order.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }
  }

  @Override
  public void newOrderTest(NewOrder order, BinanceApiCallback<Void> callback) {
    binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
        order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
        order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(), order.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  // Account endpoints

  @Override
  public void getOrderStatus(OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback) {
    binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
        orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
        orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void cancelOrder(CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback) {
    binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(),
        cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
        cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getOpenOrders(OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
    binanceApiService.getOpenOrders(orderRequest.getSymbol(),
        orderRequest.getRecvWindow(), orderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAllOrders(AllOrdersRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
    binanceApiService.getAllOrders(orderRequest.getSymbol(),
        orderRequest.getStartTime(), orderRequest.getEndTime(),
        orderRequest.getOrderId(), orderRequest.getLimit(),
        orderRequest.getRecvWindow(), orderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAccount(Long recvWindow, Long timestamp, BinanceApiCallback<Account> callback) {
    binanceApiService.getAccount(recvWindow, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAccount(BinanceApiCallback<Account> callback) {
    long timestamp = System.currentTimeMillis();
    binanceApiService.getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getDustTransferHistory(Long startTime, Long endTime,
                                     BinanceApiCallback<DustTransferLog> callback) {
    binanceApiService.getDustLog(startTime, endTime,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getRecentDustTransferHistory(BinanceApiCallback<DustTransferLog> callback) {
    getDustTransferHistory(null, null, callback);
  }

  @Override
  public void getUserAssets(String asset, boolean needBtcValuation,
                            BinanceApiCallback<List<ExtendedAssetBalance>> callback) {
    Call<List<ExtendedAssetBalance>> call = binanceApiService.getUserAssets(
        asset,
        needBtcValuation,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
        System.currentTimeMillis()
    );
    call.enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAssetDividendHistory(String asset, Long startTime, Long endTime, Integer limit,
                                      BinanceApiCallback<AssetDividendHistory> callback) {
    binanceApiService.getAssetDividendRecord(asset, startTime, endTime, limit,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getAssetDividendHistory(String asset, Long startTime, Long endTime,
                                      BinanceApiCallback<AssetDividendHistory> callback) {
    getAssetDividendHistory(asset, startTime, endTime, null, callback);
  }

  @Override
  public void getRecentAssetDividendHistory(String asset,
                                            BinanceApiCallback<AssetDividendHistory> callback) {
    getAssetDividendHistory(asset, null, null, null, callback);
  }

  @Override
  public void getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, BinanceApiCallback<List<Trade>> callback) {
    binanceApiService.getMyTrades(symbol, limit, fromId, recvWindow, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getMyTrades(String symbol, Integer limit, BinanceApiCallback<List<Trade>> callback) {
    getMyTrades(symbol, limit, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), callback);
  }

  @Override
  public void getMyTrades(String symbol, BinanceApiCallback<List<Trade>> callback) {
    getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis(), callback);
  }

  @Override
  public void withdraw(String coin, String withdrawOrderId, String network, String address, String addressTag,
                       String amount, Boolean transactionFeeFlag, String name, BinanceApiCallback<WithdrawResult> callback) {
    binanceApiService.withdraw(coin, withdrawOrderId, network, address, addressTag, amount, transactionFeeFlag, name,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getDepositHistory(String coin, Integer status, Long startTime, Long endTime,
                                Integer offset, Integer limit,
                                BinanceApiCallback<List<Deposit>> callback) {
    binanceApiService.getDepositHistory(coin, status, startTime, endTime, offset, limit,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getDepositHistory(String coin, BinanceApiCallback<List<Deposit>> callback) {
    getDepositHistory(coin, null, null, null, null, null, callback);
  }

  @Override
  public void getWithdrawHistory(String coin, String withdrawOrderId, Integer status,
                                 Integer offset, Integer limit, Long startTime, Long endTime,
                                 BinanceApiCallback<List<Withdraw>> callback) {
    Call<List<Withdraw>> call = binanceApiService.getWithdrawHistory(coin, withdrawOrderId,
        status, offset, limit, startTime, endTime, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
        System.currentTimeMillis());
    call.enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getWithdrawHistory(String coin, BinanceApiCallback<List<Withdraw>> callback) {
    getWithdrawHistory(coin, null, null, null, null, null, null, callback);
  }

  @Override
  public void getDepositAddress(String asset, String network,
                                BinanceApiCallback<DepositAddress> callback) {
    binanceApiService.getDepositAddress(asset, network,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }


  // User stream endpoints

  @Override
  public void startUserDataStream(BinanceApiCallback<ListenKey> callback) {
    binanceApiService.startUserDataStream().enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void keepAliveUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
    binanceApiService.keepAliveUserDataStream(listenKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void closeUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
    binanceApiService.closeAliveUserDataStream(listenKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  // Fiat endpoints
  @Override
  public void getFiatDepositHistory(Long startTime, Long endTime, Integer page, Integer perPage,
                                    BinanceApiCallback<FiatTransactionHistory> callback) {
    binanceApiService.getFiatDepositOrWithdrawalHistory(
            FiatTransactionType.DEPOSIT.toString(),
            startTime, endTime, page, perPage,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getFiatDepositHistory(Long startTime, Long endTime, BinanceApiCallback<FiatTransactionHistory> callback) {
    getFiatDepositHistory(startTime, endTime, null, null, callback);
  }

  @Override
  public void getRecentFiatDepositHistory(BinanceApiCallback<FiatTransactionHistory> callback) {
    getFiatDepositHistory(null, null, null, null, callback);
  }

  @Override
  public void getFiatWithdrawHistory(Long startTime, Long endTime, Integer page, Integer perPage, BinanceApiCallback<FiatTransactionHistory> callback) {
    binanceApiService.getFiatDepositOrWithdrawalHistory(
            FiatTransactionType.WITHDRAW.toString(),
            startTime, endTime, page, perPage,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getFiatWithdrawHistory(Long startTime, Long endTime, BinanceApiCallback<FiatTransactionHistory> callback) {
    getFiatWithdrawHistory(startTime, endTime, null, null, callback);
  }

  @Override
  public void getRecentFiatWithdrawHistory(BinanceApiCallback<FiatTransactionHistory> callback) {
    getFiatWithdrawHistory(null, null, null, null, callback);
  }

  @Override
  public void getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime,
                                    Integer page, Integer perPage,
                                    BinanceApiCallback<FiatPaymentHistory> callback) {
    binanceApiService.getFiatPaymentHistory(type.toString(), startTime, endTime, page, perPage,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime,
                                    BinanceApiCallback<FiatPaymentHistory> callback) {
    getFiatPaymentHistory(type, startTime, endTime, null, null, callback);
  }

  @Override
  public void getRecentFiatPaymentHistory(FiatPaymentType type,
                                          BinanceApiCallback<FiatPaymentHistory> callback) {
    getFiatPaymentHistory(type, null, null, null, null, callback);
  }

  // Savings endpoints

  @Override
  public void getSavingsInterestHistory(LendingType type, String asset,
                                        Long startTime, Long endTime,
                                        Long page, Long perPage,
                                        BinanceApiCallback<List<SavingsInterest>> callback) {
    binanceApiService.getSavingsInterestHistory(
            type.toString(), asset, startTime, endTime, page, perPage,
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }

  @Override
  public void getSavingsInterestHistory(LendingType type, String asset,
                                        Long startTime, Long endTime,
                                        BinanceApiCallback<List<SavingsInterest>> callback) {
    getSavingsInterestHistory(type, asset, startTime, endTime, null, null, callback);
  }

  @Override
  public void getRecentSavingsInterestHistory(LendingType type, String asset,
                                              BinanceApiCallback<List<SavingsInterest>> callback) {
    getSavingsInterestHistory(type, asset, null, null, null, null, callback);
  }

  @Override
  public void getLendingAccountSummary(BinanceApiCallback<LendingAccountSummary> callback) {
    binanceApiService.getLendingAccount(
            BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
        .enqueue(new BinanceApiCallbackAdapter<>(callback));
  }
}
