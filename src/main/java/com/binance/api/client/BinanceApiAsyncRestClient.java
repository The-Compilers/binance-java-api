package com.binance.api.client;

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

import java.util.List;

/**
 * Binance API facade, supporting asynchronous/non-blocking access Binance's REST API.
 */
public interface BinanceApiAsyncRestClient {

  // General endpoints

  /**
   * Test connectivity to the Rest API.
   */
  void ping(BinanceApiCallback<Void> callback);

  /**
   * Check server time.
   */
  void getServerTime(BinanceApiCallback<ServerTime> callback);

  /**
   * Current exchange trading rules and symbol information
   */
  void getExchangeInfo(BinanceApiCallback<ExchangeInfo> callback);

  /**
   * ALL supported assets and whether they can be withdrawn.
   */
  void getAllAssets(BinanceApiCallback<List<Asset>> callback);

  // Market Data endpoints

  /**
   * Get order book of a symbol (asynchronous)
   *
   * @param symbol   ticker symbol (e.g. ETHBTC)
   * @param limit    depth of the order book (max 100)
   * @param callback the callback that handles the response
   */
  void getOrderBook(String symbol, Integer limit, BinanceApiCallback<OrderBook> callback);

  /**
   * Get recent trades (up to last 500). Weight: 1
   *
   * @param symbol   ticker symbol (e.g. ETHBTC)
   * @param limit    of last trades (Default 500; max 1000.)
   * @param callback the callback that handles the response
   */
  void getTrades(String symbol, Integer limit, BinanceApiCallback<List<TradeHistoryItem>> callback);

  /**
   * Get older trades. Weight: 5
   *
   * @param symbol   ticker symbol (e.g. ETHBTC)
   * @param limit    of last trades (Default 500; max 1000.)
   * @param fromId   TradeId to fetch from. Default gets most recent trades.
   * @param callback the callback that handles the response
   */
  void getHistoricalTrades(String symbol, Integer limit, Long fromId, BinanceApiCallback<List<TradeHistoryItem>> callback);

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
   * @param callback  the callback that handles the response
   * @return a list of aggregate trades for the given symbol
   */
  void getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<AggTrade>> callback);

  /**
   * Return the most recent aggregate trades for <code>symbol</code>
   *
   * @see #getAggTrades(String, String, Integer, Long, Long, BinanceApiCallback)
   */
  void getAggTrades(String symbol, BinanceApiCallback<List<AggTrade>> callback);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @param symbol    symbol to aggregate (mandatory)
   * @param interval  candlestick interval (mandatory)
   * @param limit     Default 500; max 1000 (optional)
   * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
   * @param endTime   Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
   * @param callback  the callback that handles the response containing a candlestick bar for the given symbol and interval
   */
  void getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime, BinanceApiCallback<List<Candlestick>> callback);

  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
   *
   * @see #getCandlestickBars(String, CandlestickInterval, BinanceApiCallback)
   */
  void getCandlestickBars(String symbol, CandlestickInterval interval, BinanceApiCallback<List<Candlestick>> callback);

  /**
   * Get 24 hour price change statistics (asynchronous).
   *
   * @param symbol   ticker symbol (e.g. ETHBTC)
   * @param callback the callback that handles the response
   */
  void get24HrPriceStatistics(String symbol, BinanceApiCallback<TickerStatistics> callback);

  /**
   * Get 24 hour price change statistics for all symbols (asynchronous).
   *
   * @param callback the callback that handles the response
   */
  void getAll24HrPriceStatistics(BinanceApiCallback<List<TickerStatistics>> callback);

  /**
   * Get the latest price for all symbols (asynchronous).
   *
   * @param callback the callback that handles the response
   */
  void getAllPrices(BinanceApiCallback<List<TickerPrice>> callback);

  /**
   * Get the latest price for <code>symbol</code> (asynchronous).
   *
   * @param symbol   ticker symbol (e.g. ETHBTC)
   * @param callback the callback that handles the response
   */
  void getPrice(String symbol, BinanceApiCallback<TickerPrice> callback);

  /**
   * Get best price/qty on the order book for all symbols (asynchronous).
   *
   * @param callback the callback that handles the response
   */
  void getBookTickers(BinanceApiCallback<List<BookTicker>> callback);

  // Account endpoints

  /**
   * Send in a new order (asynchronous)
   *
   * @param order    the new order to submit.
   * @param callback the callback that handles the response
   */
  void newOrder(NewOrder order, BinanceApiCallback<NewOrderResponse> callback);

  /**
   * Test new order creation and signature/recvWindow long. Creates and validates a new order but does not send it into the matching engine.
   *
   * @param order    the new TEST order to submit.
   * @param callback the callback that handles the response
   */
  void newOrderTest(NewOrder order, BinanceApiCallback<Void> callback);

  /**
   * Check an order's status (asynchronous).
   *
   * @param orderStatusRequest order status request parameters
   * @param callback           the callback that handles the response
   */
  void getOrderStatus(OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback);

  /**
   * Cancel an active order (asynchronous).
   *
   * @param cancelOrderRequest order status request parameters
   * @param callback           the callback that handles the response
   */
  void cancelOrder(CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback);

  /**
   * Get all open orders on a symbol (asynchronous).
   *
   * @param orderRequest order request parameters
   * @param callback     the callback that handles the response
   */
  void getOpenOrders(OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback);

  /**
   * Get all account orders; active, canceled, or filled.
   *
   * @param orderRequest order request parameters
   * @param callback     the callback that handles the response
   */
  void getAllOrders(AllOrdersRequest orderRequest, BinanceApiCallback<List<Order>> callback);

  /**
   * Get current account information (async).
   */
  void getAccount(Long recvWindow, Long timestamp, BinanceApiCallback<Account> callback);

  /**
   * Get current account information using default parameters (async).
   *
   * @param callback the callback that handles the response
   */
  void getAccount(BinanceApiCallback<Account> callback);

  /**
   * Get history of dust transfer transactions, max 100 records per request.
   * Warning: The API Only return records after 2020/12/01 !!! You need to manually fetch
   * earlier records, using the "Transaction history CSV" on the Binance webpage!
   *
   * @param startTime When specified, return only transactions with time >= startTime
   * @param endTime   When specified, return only transactions with time <= endTime
   * @param callback  the callback that handles the response
   */
  void getDustTransferHistory(Long startTime, Long endTime,
                              BinanceApiCallback<DustTransferLog> callback);

  /**
   * Get recent history of dust transfer transactions, max 100 records.
   * @param callback  the callback that handles the response
   */
  void getRecentDustTransferHistory(BinanceApiCallback<DustTransferLog> callback);

  /**
   * Get User account information.
   *
   * @param asset            When specified, include only the given asset. When null, get
   *                         balances for all assets
   * @param needBtcValuation When true, include value in BTC for each asset.
   * @param callback         the callback that handles the response
   */
  void getUserAssets(String asset, boolean needBtcValuation,
                     BinanceApiCallback<List<ExtendedAssetBalance>> callback);

  /**
   * Get asset dividend record (history).
   *
   * @param asset     The asset to query
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param limit     Maximum records to return. Default 20 (when null), max 500.
   * @param callback  the callback that handles the response
   */
  void getAssetDividendHistory(String asset, Long startTime, Long endTime, Integer limit,
                               BinanceApiCallback<AssetDividendHistory> callback);

  /**
   * Get asset dividend record (history), with the default limit value.
   *
   * @param asset     The asset to query
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param callback  the callback that handles the response
   */
  void getAssetDividendHistory(String asset, Long startTime, Long endTime,
                               BinanceApiCallback<AssetDividendHistory> callback);

  /**
   * Get recent asset dividend record (history).
   *
   * @param asset    The asset to query
   * @param callback the callback that handles the response
   */
  void getRecentAssetDividendHistory(String asset,
                                     BinanceApiCallback<AssetDividendHistory> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol   symbol to get trades from
   * @param limit    default 500; max 1000
   * @param fromId   TradeId to fetch from. Default gets most recent trades.
   * @param callback the callback that handles the response with a list of trades
   */
  void getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp, BinanceApiCallback<List<Trade>> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol   symbol to get trades from
   * @param limit    default 500; max 1000
   * @param callback the callback that handles the response with a list of trades
   */
  void getMyTrades(String symbol, Integer limit, BinanceApiCallback<List<Trade>> callback);

  /**
   * Get trades for a specific account and symbol.
   *
   * @param symbol   symbol to get trades from
   * @param callback the callback that handles the response with a list of trades
   */
  void getMyTrades(String symbol, BinanceApiCallback<List<Trade>> callback);

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
   * @param transactionFeeFlag When making internal transfer, true for returning the fee to the
   *                           destination account; false for returning the fee back to the
   *                           departure account. Default false.
   * @param name               Description of the address. Space in name should be encoded into %20.
   * @param callback           the callback that handles the response with a list of trades
   */
  void withdraw(String coin, String withdrawOrderId, String network, String address,
                String addressTag, String amount, Boolean transactionFeeFlag, String name,
                BinanceApiCallback<WithdrawResult> callback);

  /**
   * Fetch account deposit history.
   *
   * @param coin     the asset to get the history for
   * @param callback the callback that handles the response and returns the deposit history
   */
  void getDepositHistory(String coin, BinanceApiCallback<List<Deposit>> callback);


  /**
   * Fetch account deposit history.
   *
   * @param coin      the asset to get the history for
   * @param status    Status, with the following values:
   *                  - 0: pending
   *                  - 6: credited but cannot withdraw
   *                  - 1: success
   * @param startTime Start timestamp, including milliseconds. Default: 90 days before current
   *                  timestamp
   * @param endTime   End timestamp, including milliseconds. Default: present timestamp
   * @param offset    Default:0
   * @param limit     Default:1000, Max:1000
   * @param callback  the callback that handles the response and returns the deposit history
   */
  void getDepositHistory(String coin, Integer status, Long startTime, Long endTime,
                         Integer offset, Integer limit, BinanceApiCallback<List<Deposit>> callback);

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
   * @param callback        the callback that handles the response and returns the withdrawal history
   */
  void getWithdrawHistory(String coin, String withdrawOrderId, Integer status,
                          Integer offset, Integer limit, Long startTime, Long endTime,
                          BinanceApiCallback<List<Withdraw>> callback);

  /**
   * Fetch account withdraw history.
   *
   * @param coin     The coin in the withdrawals
   * @param callback the callback that handles the response and returns the withdrawal history
   */
  void getWithdrawHistory(String coin,
                          BinanceApiCallback<List<Withdraw>> callback);

  /**
   * Fetch deposit address.
   *
   * @param asset    The asset for which to check the deposit address
   * @param network  The network to use for deposit
   * @param callback the callback that handles the response and returns the deposit address
   */
  void getDepositAddress(String asset, String network, BinanceApiCallback<DepositAddress> callback);

  // User stream endpoints

  /**
   * Start a new user data stream.
   *
   * @param callback the callback that handles the response which contains a listenKey
   */
  void startUserDataStream(BinanceApiCallback<ListenKey> callback);

  /**
   * PING a user data stream to prevent a time out.
   *
   * @param listenKey listen key that identifies a data stream
   * @param callback  the callback that handles the response which contains a listenKey
   */
  void keepAliveUserDataStream(String listenKey, BinanceApiCallback<Void> callback);

  /**
   * Close out a new user data stream.
   *
   * @param listenKey listen key that identifies a data stream
   * @param callback  the callback that handles the response which contains a listenKey
   */
  void closeUserDataStream(String listenKey, BinanceApiCallback<Void> callback);

  // Fiat endpoints

  /**
   * Get FIAT currency deposit history.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param page      The page number, if there are multiple pages
   * @param perPage   Number of records per page
   * @param callback  Callback which will handle the result
   */
  void getFiatDepositHistory(Long startTime, Long endTime, Integer page, Integer perPage,
                             BinanceApiCallback<FiatTransactionHistory> callback);

  /**
   * Get FIAT currency deposit history, with default paging.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param callback  Callback which will handle the result
   */
  void getFiatDepositHistory(Long startTime, Long endTime,
                             BinanceApiCallback<FiatTransactionHistory> callback);

  /**
   * Get recent FIAT currency deposit history.
   *
   * @param callback Callback which will handle the result
   */
  void getRecentFiatDepositHistory(BinanceApiCallback<FiatTransactionHistory> callback);

  /**
   * Get FIAT currency withdrawal history.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param page      The page number, if there are multiple pages
   * @param perPage   Number of rows (records) per page
   * @param callback  Callback which will handle the result
   */
  void getFiatWithdrawHistory(Long startTime, Long endTime, Integer page, Integer perPage,
                              BinanceApiCallback<FiatTransactionHistory> callback);


  /**
   * Get FIAT currency withdrawal history, with default paging.
   *
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param callback  Callback which will handle the result
   */
  void getFiatWithdrawHistory(Long startTime, Long endTime,
                              BinanceApiCallback<FiatTransactionHistory> callback);

  /**
   * Get recent FIAT currency withdrawal history.
   *
   * @param callback Callback which will handle the result
   */
  void getRecentFiatWithdrawHistory(BinanceApiCallback<FiatTransactionHistory> callback);

  /**
   * Get FIAT currency payment history (Credit card purchases, bank transfers).
   *
   * @param type      Filter by payment type: buy or sell
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param page      The page number, if there are multiple pages
   * @param perPage   Number of rows (records) per page
   * @param callback  Callback which will handle the result
   */
  void getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime,
                             Integer page, Integer perPage,
                             BinanceApiCallback<FiatPaymentHistory> callback);

  /**
   * Get FIAT currency payment history, with default paging parameters.
   *
   * @param type      Filter by payment type: buy or sell
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param callback  Callback which will handle the result
   */
  void getFiatPaymentHistory(FiatPaymentType type, Long startTime, Long endTime,
                             BinanceApiCallback<FiatPaymentHistory> callback);

  /**
   * Get recent FIAT currency payment history (Credit card purchases, bank transfers).
   *
   * @param type     Filter by payment type: buy or sell
   * @param callback Callback which will handle the result
   */
  void getRecentFiatPaymentHistory(FiatPaymentType type,
                                   BinanceApiCallback<FiatPaymentHistory> callback);

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
   * @param callback  Callback which will handle the result
   */
  void getSavingsInterestHistory(LendingType type, String asset, Long startTime,
                                 Long endTime, Long page, Long perPage,
                                 BinanceApiCallback<List<SavingsInterest>> callback);

  /**
   * Get savings interest history for the user's account, default paging settings.
   *
   * @param type      The type of lending
   * @param asset     The asset in question
   * @param startTime Return only transactions where time >= startTime
   * @param endTime   Return only transactions where time <= endTime
   * @param callback  Callback which will handle the result
   */
  void getSavingsInterestHistory(LendingType type, String asset, Long startTime,
                                 Long endTime,
                                 BinanceApiCallback<List<SavingsInterest>> callback);

  /**
   * Get recent savings interest history for the user's account.
   *
   * @param type     The type of lending
   * @param asset    The asset in question
   * @param callback Callback which will handle the result
   */
  void getRecentSavingsInterestHistory(LendingType type, String asset,
                                       BinanceApiCallback<List<SavingsInterest>> callback);

  /**
   * Get the summary of all lending accounts.
   *
   * @param callback Callback which will handle the result
   */
  void getLendingAccountSummary(BinanceApiCallback<LendingAccountSummary> callback);
}