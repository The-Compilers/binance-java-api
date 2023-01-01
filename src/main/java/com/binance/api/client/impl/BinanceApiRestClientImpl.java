package com.binance.api.client.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.*;
import com.binance.api.client.domain.fiat.FiatPaymentHistory;
import com.binance.api.client.domain.fiat.FiatPaymentType;
import com.binance.api.client.domain.fiat.FiatTransactionHistory;
import com.binance.api.client.domain.fiat.FiatTransactionType;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.market.*;
import retrofit2.Call;

import java.util.List;

import static com.binance.api.client.impl.BinanceApiServiceGenerator.createService;
import static com.binance.api.client.impl.BinanceApiServiceGenerator.executeSync;

/**
 * Implementation of Binance's REST API using Retrofit with synchronous/blocking
 * method calls.
 */
public class BinanceApiRestClientImpl implements BinanceApiRestClient {

  private final BinanceApiService binanceApiService;

  public BinanceApiRestClientImpl(String apiKey, String secret) {
    binanceApiService = createService(BinanceApiService.class, apiKey, secret);
  }

  // General endpoints

  @Override
  public void ping() {
    executeSync(binanceApiService.ping());
  }

  @Override
  public Long getServerTime() {
    return executeSync(binanceApiService.getServerTime()).getServerTime();
  }

  @Override
  public ExchangeInfo getExchangeInfo() {
    return executeSync(binanceApiService.getExchangeInfo());
  }

  @Override
  public List<Asset> getAllAssets() {
    return executeSync(binanceApiService
        .getAllAssets(BinanceApiConfig.getAssetInfoApiBaseUrl() + "assetWithdraw/getAllAsset.html"));
  }

  // Market Data endpoints

  @Override
  public OrderBook getOrderBook(String symbol, Integer limit) {
    return executeSync(binanceApiService.getOrderBook(symbol, limit));
  }

  @Override
  public List<TradeHistoryItem> getTrades(String symbol, Integer limit) {
    return executeSync(binanceApiService.getTrades(symbol, limit));
  }

  @Override
  public List<TradeHistoryItem> getHistoricalTrades(String symbol, Integer limit, Long fromId) {
    return executeSync(binanceApiService.getHistoricalTrades(symbol, limit, fromId));
  }

  @Override
  public List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime) {
    return executeSync(binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime));
  }

  @Override
  public List<AggTrade> getAggTrades(String symbol) {
    return getAggTrades(symbol, null, null, null, null);
  }

  @Override
  public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit,
                                              Long startTime, Long endTime) {
    return executeSync(
        binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime));
  }

  @Override
  public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval) {
    return getCandlestickBars(symbol, interval, null, null, null);
  }

  @Override
  public TickerStatistics get24HrPriceStatistics(String symbol) {
    return executeSync(binanceApiService.get24HrPriceStatistics(symbol));
  }

  @Override
  public List<TickerStatistics> getAll24HrPriceStatistics() {
    return executeSync(binanceApiService.getAll24HrPriceStatistics());
  }

  @Override
  public TickerPrice getPrice(String symbol) {
    return executeSync(binanceApiService.getLatestPrice(symbol));
  }

  @Override
  public List<TickerPrice> getAllPrices() {
    return executeSync(binanceApiService.getLatestPrices());
  }

  @Override
  public List<BookTicker> getBookTickers() {
    return executeSync(binanceApiService.getBookTickers());
  }

  @Override
  public NewOrderResponse newOrder(NewOrder order) {
    final Call<NewOrderResponse> call;
    if (order.getQuoteOrderQty() == null) {
      call = binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
          order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(),
          order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
          order.getTimestamp());
    } else {
      call = binanceApiService.newOrderQuoteQty(order.getSymbol(), order.getSide(), order.getType(),
          order.getTimeInForce(), order.getQuoteOrderQty(), order.getPrice(), order.getNewClientOrderId(),
          order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
          order.getTimestamp());
    }
    return executeSync(call);
  }

  @Override
  public void newOrderTest(NewOrder order) {
    executeSync(binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
        order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(),
        order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(),
        order.getTimestamp()));
  }

  // Account endpoints

  @Override
  public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
    return executeSync(binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
        orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
        orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
  }

  @Override
  public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
    return executeSync(
        binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(), cancelOrderRequest.getOrderId(),
            cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
            cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
  }

  @Override
  public List<Order> getOpenOrders(OrderRequest orderRequest) {
    return executeSync(binanceApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getRecvWindow(),
        orderRequest.getTimestamp()));
  }

  @Override
  public List<Order> getAllOrders(AllOrdersRequest orderRequest) {
    return executeSync(binanceApiService.getAllOrders(orderRequest.getSymbol(), orderRequest.getOrderId(),
        orderRequest.getLimit(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
  }

  @Override
  public CancelOrderListResponse cancelOrderList(CancelOrderListRequest cancelOrderListRequest) {
    return executeSync(binanceApiService.cancelOrderList(cancelOrderListRequest.getSymbol(), cancelOrderListRequest.getOrderListId(),
        cancelOrderListRequest.getListClientOrderId(), cancelOrderListRequest.getNewClientOrderId(),
        cancelOrderListRequest.getRecvWindow(), cancelOrderListRequest.getTimestamp()));
  }

  @Override
  public OrderList getOrderListStatus(OrderListStatusRequest orderListStatusRequest) {
    return executeSync(binanceApiService.getOrderListStatus(orderListStatusRequest.getOrderListId(), orderListStatusRequest.getOrigClientOrderId(),
        orderListStatusRequest.getRecvWindow(), orderListStatusRequest.getTimestamp()));
  }

  @Override
  public List<OrderList> getAllOrderList(AllOrderListRequest allOrderListRequest) {
    return executeSync(binanceApiService.getAllOrderList(allOrderListRequest.getFromId(), allOrderListRequest.getStartTime(),
        allOrderListRequest.getEndTime(), allOrderListRequest.getLimit(), allOrderListRequest.getRecvWindow(), allOrderListRequest.getTimestamp()));
  }

  @Override
  public Account getAccount() {
    return executeSync(binanceApiService.getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
        System.currentTimeMillis()));
  }

  @Override
  public List<Trade> getMyTrades(String symbol, Integer limit, Long fromId) {
    return executeSync(binanceApiService.getMyTrades(symbol, limit, fromId,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public List<Trade> getMyTrades(String symbol, Integer limit) {
    return getMyTrades(symbol, limit, null);
  }

  @Override
  public List<Trade> getMyTrades(String symbol) {
    return getMyTrades(symbol, null, null);
  }

  @Override
  public List<Trade> getMyTrades(String symbol, Long fromId) {
    return getMyTrades(symbol, null, fromId);
  }

  @Override
  public WithdrawResult withdraw(String coin, String withdrawOrderId, String network,
                                 String address, String amount, String name, String addressTag,
                                 Boolean transactionFeeFlag) {
    return executeSync(binanceApiService.withdraw(coin, withdrawOrderId, network, address,
        addressTag, amount, transactionFeeFlag, name,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public DustTransferResponse dustTransfer(List<String> asset) {
    return executeSync(binanceApiService.dustTransfer(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public List<Deposit> getDepositHistory(String coin) {
    return getDepositHistory(coin, null, null, null, null, null);
  }

  @Override
  public List<Deposit> getDepositHistory(String coin, Integer status, Long startTime, Long endTime,
                                         Integer offset, Integer limit) {
    return executeSync(binanceApiService.getDepositHistory(coin, status, startTime, endTime,
        offset, limit,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public List<Withdraw> getWithdrawHistory(String coin, String withdrawOrderId, Integer status,
                                           Integer offset, Integer limit,
                                           Long startTime, Long endTime) {
    return executeSync(binanceApiService.getWithdrawHistory(coin, withdrawOrderId, status, offset,
        limit, startTime, endTime, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
        System.currentTimeMillis()));
  }

  @Override
  public List<Withdraw> getWithdrawHistory(String coin) {
    return getWithdrawHistory(coin, null, null, null, null, null, null);
  }

  @Override
  public DepositAddress getDepositAddress(String coin, String network) {
    return executeSync(binanceApiService.getDepositAddress(coin, network,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public List<ExtendedAssetBalance> getUserAssets(String asset, boolean needBtcValuation) {
    return executeSync(binanceApiService.getUserAssets(asset, needBtcValuation,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
    );
  }

  @Override
  public AssetDividendHistory getAssetDividendHistory(String asset, Long startTime, Long endTime,
                                                     Integer limit) {
    return executeSync(binanceApiService.getAssetDividendRecord(asset, startTime, endTime, limit,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis())
    );
  }

  @Override
  public AssetDividendHistory getAssetDividendHistory(String asset, Long startTime, Long endTime) {
    return getAssetDividendHistory(asset, startTime, endTime, null);
  }

  @Override
  public AssetDividendHistory getRecentAssetDividendHistory(String asset) {
    return getAssetDividendHistory(asset, null, null, null);
  }

  // User stream endpoints

  @Override
  public String startUserDataStream() {
    return executeSync(binanceApiService.startUserDataStream()).toString();
  }

  @Override
  public void keepAliveUserDataStream(String listenKey) {
    executeSync(binanceApiService.keepAliveUserDataStream(listenKey));
  }

  @Override
  public void closeUserDataStream(String listenKey) {
    executeSync(binanceApiService.closeAliveUserDataStream(listenKey));
  }

  @Override
  public FiatTransactionHistory getFiatDepositHistory(Long startTime, Long endTime,
                                                      Integer page, Integer perPage) {
    return executeSync(binanceApiService.getFiatDepositOrWithdrawalHistory(
        FiatTransactionType.DEPOSIT.toString(), startTime, endTime, page, perPage,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public FiatTransactionHistory getFiatDepositHistory(Long startTime, Long endTime) {
    return getFiatDepositHistory(startTime, endTime, null, null);
  }

  @Override
  public FiatTransactionHistory getRecentFiatDepositHistory() {
    return getFiatDepositHistory(null, null, null, null);
  }

  @Override
  public FiatTransactionHistory getFiatWithdrawHistory(Long startTime, Long endTime,
                                                       Integer page, Integer perPage) {
    return executeSync(binanceApiService.getFiatDepositOrWithdrawalHistory(
        FiatTransactionType.WITHDRAW.toString(), startTime, endTime, page, perPage,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public FiatTransactionHistory getFiatWithdrawHistory(Long startTime, Long endTime) {
    return getFiatWithdrawHistory(startTime, endTime, null, null);
  }

  @Override
  public FiatTransactionHistory getRecentFiatWithdrawHistory() {
    return getFiatWithdrawHistory(null, null, null, null);
  }

  @Override
  public FiatPaymentHistory getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime,
                                                  Integer page, Integer perPage) {
    return executeSync(binanceApiService.getFiatPaymentHistory(type.toString(),
        startTime, endTime, page, perPage,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public FiatPaymentHistory getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime) {
    return getFiatPaymentHistory(type, startTime, endTime, null, null);
  }

  @Override
  public FiatPaymentHistory getRecentFiatPaymentHistory(FiatPaymentType type) {
    return getFiatPaymentHistory(type, null, null, null, null);
  }

  // Savings endpoints

  @Override
  public List<SavingsInterest> getSavingsInterestHistory(LendingType type, String asset,
                                                         Long startTime, Long endTime,
                                                         Long page, Long perPage) {
    return executeSync(binanceApiService.getSavingsInterestHistory(type.toString(), asset,
        startTime, endTime, page, perPage,
        BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public List<SavingsInterest> getSavingsInterestHistory(LendingType type, String asset,
                                                         Long startTime, Long endTime) {
    return getSavingsInterestHistory(type, asset, startTime, endTime, null, null);
  }

  @Override
  public List<SavingsInterest> getRecentSavingsInterestHistory(LendingType type, String asset) {
    return getSavingsInterestHistory(type, asset, null, null, null, null);
  }
}
