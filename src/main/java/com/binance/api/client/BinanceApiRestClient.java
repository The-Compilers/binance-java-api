package com.binance.api.client;

import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.*;
import com.binance.api.client.domain.fiat.FiatPaymentHistory;
import com.binance.api.client.domain.fiat.FiatPaymentType;
import com.binance.api.client.domain.fiat.FiatTransactionHistory;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.domain.market.TickerStatistics;

import java.util.List;

/**
 * Binance API facade, supporting synchronous/blocking access Binance's REST API.
 */
public interface BinanceApiRestClient {

  // General endpoints

  /**
   * Test connectivity to the Rest API.
   */
  void ping();

  /**
   * Test connectivity to the Rest API and get the current server time.
   *
   * @return current server time.
   */
  Long getServerTime();

  /**
   * @return Current exchange trading rules and symbol information
   */
  ExchangeInfo getExchangeInfo();

  /**
   * @return All the supported assets and whether they can be withdrawn.
   */
  List<Asset> getAllAssets();

  // Market Data endpoints

  /**
   * Get order book of a symbol.
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param limit  depth of the order book (max 100)
   */
  OrderBook getOrderBook(String symbol, Integer limit);

  /**
   * Get recent trades (up to last 500). Weight: 1
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param limit  of last trades (Default 500; max 1000.)
   */
  List<TradeHistoryItem> getTrades(String symbol, Integer limit);

  /**
   * Get older trades. Weight: 5
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   * @param limit  of last trades (Default 500; max 1000.)
   * @param fromId TradeId to fetch from. Default gets most recent trades.
   */
  List<TradeHistoryItem> getHistoricalTrades(String symbol, Integer limit, Long fromId);

  /**
   * Get compressed, aggregate trades. Trades that fill at the time, from the same order, with
   * the same price will have the quantity aggregated.
   * <p>
   * If both <code>startTime</code> and <code>endTime</code> are sent, <code>limit</code>should not
   * be sent AND the distance between <code>startTime</code> and <code>endTime</code> must be less than 24 hours.
   *
   * @param symbol    symbol to aggregate (mandatory)
   * @param fromId    ID to get aggregate trades from INCLUSIVE (optional)
   * @param limit     Default 500; max 1000 (optional)
   * @param startTime Timestamp in ms to get aggregate trades from INCLUSIVE (optional).
   * @param endTime   Timestamp in ms to get aggregate trades until INCLUSIVE (optional).
   * @return a list of aggregate trades for the given symbol
   */
  List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime);

  /**
   * Return the most recent aggregate trades for <code>symbol</code>
   *
   * @see #getAggTrades(String, String, Integer, Long, Long)
   */
  List<AggTrade> getAggTrades(String symbol);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @param symbol    symbol to aggregate (mandatory)
   * @param interval  candlestick interval (mandatory)
   * @param limit     Default 500; max 1000 (optional)
   * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
   * @param endTime   Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
   * @return a candlestick bar for the given symbol and interval
   */
  List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @see #getCandlestickBars(String, CandlestickInterval, Integer, Long, Long)
   */
  List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval);

  /**
   * Get 24 hour price change statistics.
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   */
  TickerStatistics get24HrPriceStatistics(String symbol);

  /**
   * Get 24 hour price change statistics for all symbols.
   */
  List<TickerStatistics> getAll24HrPriceStatistics();

  /**
   * Get the latest price for all symbols.
   */
  List<TickerPrice> getAllPrices();

  /**
   * Get the latest price for <code>symbol</code>.
   *
   * @param symbol ticker symbol (e.g. ETHBTC)
   */
  TickerPrice getPrice(String symbol);

  /**
   * Get best price/qty on the order book for all symbols.
   */
  List<BookTicker> getBookTickers();

  // Account endpoints

  /**
   * Send in a new order.
   *
   * @param order the new order to submit.
   * @return a response containing details about the newly placed order.
   */
  NewOrderResponse newOrder(NewOrder order);

  /**
   * Test new order creation and signature/recvWindow long. Creates and validates a new order but does not send it into the matching engine.
   *
   * @param order the new TEST order to submit.
   */
  void newOrderTest(NewOrder order);

  /**
   * Check an order's status.
   *
   * @param orderStatusRequest order status request options/filters
   * @return an order
   */
  Order getOrderStatus(OrderStatusRequest orderStatusRequest);

  /**
   * Cancel an active order.
   *
   * @param cancelOrderRequest order status request parameters
   */
  CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest);

  /**
   * Get all open orders on a symbol.
   *
   * @param orderRequest order request parameters
   * @return a list of all account open orders on a symbol.
   */
  List<Order> getOpenOrders(OrderRequest orderRequest);

  /**
   * Get all account orders; active, canceled, or filled.
   *
   * @param orderRequest order request parameters
   * @return a list of all account orders
   */
  List<Order> getAllOrders(AllOrdersRequest orderRequest);

  /**
   * Cancel an entire Order List
   *
   * @return
   */
  CancelOrderListResponse cancelOrderList(CancelOrderListRequest cancelOrderListRequest);

  /**
   * Check an order list status
   *
   * @param orderListStatusRequest
   * @return an orderList
   */
  OrderList getOrderListStatus(OrderListStatusRequest orderListStatusRequest);

  /**
   * Get all list os orders
   *
   * @param allOrderListRequest
   * @return
   */
  List<OrderList> getAllOrderList(AllOrderListRequest allOrderListRequest);

  /**
   * Get current account information.
   */
  Account getAccount();

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param limit  default 500; max 1000
   * @param fromId TradeId to fetch from. Default gets most recent trades.
   * @return a list of trades
   */
  List<Trade> getMyTrades(String symbol, Integer limit, Long fromId);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @param limit  default 500; max 1000
   * @return a list of trades
   */
  List<Trade> getMyTrades(String symbol, Integer limit);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol symbol to get trades from
   * @return a list of trades
   */
  List<Trade> getMyTrades(String symbol);

  List<Trade> getMyTrades(String symbol, Long fromId);

  /**
   * Submit a withdrawal request.
   * Withdrawals option has to be active in the API settings.
   *
   * @param coin               asset symbol to withdraw
   * @param withdrawOrderId    client id for withdraw
   * @param network            the network to use for the withdrawal
   * @param address            address to withdraw to
   * @param addressTag         Secondary address identifier for coins like XRP,XMR etc.
   * @param amount             amount to withdraw
   * @param transactionFeeFlag When making internal transfer, true for returning the fee to the destination account; false for returning the fee back to the departure account. Default false.
   * @param name               Description of the address. Space in name should be encoded into %20.
   */
  WithdrawResult withdraw(String coin, String withdrawOrderId, String network, String address, String amount,
                          String name, String addressTag, Boolean transactionFeeFlag);

  /**
   * Conver a list of assets to BNB
   *
   * @param asset the list of assets to convert
   */
  DustTransferResponse dustTransfer(List<String> asset);

  /**
   * Fetch account deposit history.
   *
   * @return deposit history, containing a list of deposits
   */
  List<Deposit> getDepositHistory(String asset);

  /**
   * Fetch account deposit history.
   *
   * @param coin      the asset to get the history for
   * @param status    When specified, filter by transaction status:
   *                  - 0: pending
   *                  - 6: credited but cannot withdraw
   *                  - 1: success)
   * @param startTime Default: 90 days from current timestamp
   * @param endTime   Default: present timestamp
   * @param offset    Default:0
   * @param limit     Default:1000, Max:1000
   * @return deposit history, containing a list of deposits
   */
  List<Deposit> getDepositHistory(String coin, Integer status, Long startTime, Long endTime,
                                  Integer offset, Integer limit);

  /**
   * Fetch account withdraw history.
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
   * @return withdraw history, containing a list of withdrawals
   */
  List<Withdraw> getWithdrawHistory(String coin, String withdrawOrderId, Integer status,
                                    Integer offset, Integer limit, Long startTime, Long endTime);

  /**
   * Fetch account withdraw history, with default values.
   * See https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data
   *
   * @param coin The coin to look withdrawals for
   * @return withdraw history, containing a list of withdrawals
   */
  List<Withdraw> getWithdrawHistory(String coin);

  /**
   * Fetch deposit address.
   *
   * @param coin    The coin to get the address for
   * @param network The network to use for deposit
   * @return deposit address for a given asset.
   */
  DepositAddress getDepositAddress(String coin, String network);

  /**
   * Get User account information.
   *
   * @param asset            When specified, include only the given asset. When null, get
   *                         balances for all assets
   * @param needBtcValuation When true, include value in BTC for each asset.
   * @return A list of all user assets with non-zero balances (or only the one asset,
   * when specified)
   */
  List<ExtendedAssetBalance> getUserAssets(String asset, boolean needBtcValuation);

  /**
   * Get asset dividend record (history).
   *
   * @param asset     The asset to query
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param limit     Maximum records to return. Default 20 (when null), max 500.
   * @return List of asset dividend records
   */
  AssetDividendHistory getAssetDividendHistory(String asset, Long startTime, Long endTime,
                                              Integer limit);

  /**
   * Get asset dividend record (history), with the default limit value.
   *
   * @param asset     The asset to query
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @return List of asset dividend records
   */
  AssetDividendHistory getAssetDividendHistory(String asset, Long startTime, Long endTime);

  /**
   * Get recent asset dividend record (history).
   *
   * @param asset     The asset to query
   * @return List of asset dividend records
   */
  AssetDividendHistory getRecentAssetDividendHistory(String asset);


  // User stream endpoints

  /**
   * Start a new user data stream.
   *
   * @return a listen key that can be used with data streams
   */
  String startUserDataStream();

  /**
   * PING a user data stream to prevent a time out.
   *
   * @param listenKey listen key that identifies a data stream
   */
  void keepAliveUserDataStream(String listenKey);

  /**
   * Close out a new user data stream.
   *
   * @param listenKey listen key that identifies a data stream
   */
  void closeUserDataStream(String listenKey);

  // Fiat endpoints

  /**
   * Get FIAT currency deposit history.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param page      The page number, if there are multiple pages
   * @param perPage   Number of rows (records) per page
   * @return Deposit history, with a list of FiatTransaction objects inside
   */
  FiatTransactionHistory getFiatDepositHistory(Long startTime, Long endTime,
                                               Integer page, Integer perPage);

  /**
   * Get FIAT currency deposit history, with default paging parameters.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @return Deposit history, with a list of FiatTransaction objects inside
   */
  FiatTransactionHistory getFiatDepositHistory(Long startTime, Long endTime);

  /**
   * Get recent FIAT currency deposit history.
   *
   * @return Deposit history, with a list of FiatTransaction objects inside
   */
  FiatTransactionHistory getRecentFiatDepositHistory();

  /**
   * Get FIAT currency withdrawal history.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param page      The page number, if there are multiple pages
   * @param perPage   Number of rows (records) per page
   * @return Withdrawal history, with a list of FiatTransaction objects inside
   */
  FiatTransactionHistory getFiatWithdrawHistory(Long startTime, Long endTime,
                                                Integer page, Integer perPage);

  /**
   * Get FIAT currency withdrawal history, with default paging parameters.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @return Withdrawal history, with a list of FiatTransaction objects inside
   */
  FiatTransactionHistory getFiatWithdrawHistory(Long startTime, Long endTime);


  /**
   * Get recent FIAT currency withdrawal history.
   *
   * @return Withdrawal history, with a list of FiatTransaction objects inside
   */
  FiatTransactionHistory getRecentFiatWithdrawHistory();

  /**
   * Get FIAT currency payment history (Credit card purchases, bank transfers).
   *
   * @param type      Filter by payment type: buy or sell
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param page      The page number, if there are multiple pages
   * @param perPage   Number of rows (records) per page
   * @return Payment history, with a list of FiatPayment objects inside
   */
  FiatPaymentHistory getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime,
                                           Integer page, Integer perPage);

  /**
   * Get FIAT currency payment history, with default paging parameters.
   *
   * @param type      Filter by payment type: buy or sell
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @return Payment history, with a list of FiatPayment objects inside
   */
  FiatPaymentHistory getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime);

  /**
   * Get recent FIAT currency payment history (Credit card purchases, bank transfers).
   *
   * @param type Filter by payment type: buy or sell
   * @return Payment history, with a list of FiatPayment objects inside
   */
  FiatPaymentHistory getRecentFiatPaymentHistory(FiatPaymentType type);

  // Savings endpoints

  /**
   * Get savings interest history for the user's account.
   *
   * @param type      The type of lending
   * @param asset     The asset in question
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param page      The page number, if there are multiple pages
   * @param perPage   Number of records per page
   * @return
   */
  List<SavingsInterest> getSavingsInterestHistory(LendingType type, String asset, Long startTime,
                                                  Long endTime, Long page, Long perPage);

  /**
   * Get savings interest history for the user's account, default paging settings.
   *
   * @param type      The type of lending
   * @param asset     The asset in question
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @return
   */
  List<SavingsInterest> getSavingsInterestHistory(LendingType type, String asset, Long startTime,
                                                  Long endTime);

  /**
   * Get recent savings interest history for the user's account.
   *
   * @param type  The type of lending
   * @param asset The asset in question
   * @return
   */
  List<SavingsInterest> getRecentSavingsInterestHistory(LendingType type, String asset);
}
