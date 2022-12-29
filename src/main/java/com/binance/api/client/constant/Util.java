package com.binance.api.client.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class.
 */
public final class Util {

  /**
   * List of fiat currencies.
   */
  private static final Set<String> FIAT_CURRENCIES = new HashSet<>(
      Arrays.asList("EUR", "USDT", "BUSD", "PAX", "TUSD", "USDC", "NGN", "RUB", "USDS", "TRY")
  );

  /**
   * List of assets which are present on Binance but are not traded.
   */
  private static final Set<String> NON_TRADED_ASSETS = new HashSet<>(
      Arrays.asList("DON", "NFT", "ETHW")
  );

  // All currencies placed in Earnings account are assigned this prefix
  private static final String EARNINGS_CURRENCY_PREFIX = "LD";

  public static final String BTC_TICKER = "BTC";

  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  private Util() {
  }

  /**
   * Check if the asset is fiat currency.
   *
   * @return True if it is a fiat currency (USD, EUR, etc), false when it isn't
   */
  public static boolean isFiatCurrency(String symbol) {
    return FIAT_CURRENCIES.contains(symbol);
  }


  /**
   * Check if the asset is a currency deposited in an Earnings account.
   *
   * @return True if it is an earnings currency, false when it isn't
   */
  public static boolean isEarningsCurrency(String asset) {
    return asset != null && asset.startsWith(EARNINGS_CURRENCY_PREFIX);
  }

  /**
   * Convert an earnings asset symbol to normal currency symbol.
   *
   * @param asset The earnings asset symbol
   * @return The regular asset (coin) symbol
   */
  public static String earningSymbolToCoin(String asset) {
    if (!isEarningsCurrency(asset)) {
      throw new IllegalArgumentException(asset + " is not an asset in earnings account");
    }
    return asset.substring(2);
  }

  /**
   * Check if the given asset is traded on the exchange (some tokens are not, such as DON).
   *
   * @param asset The asset to check
   * @return True if it is traded, false if it is not.
   */
  public static boolean isTraded(String asset) {
    return !NON_TRADED_ASSETS.contains(asset);
  }

  /**
   * Convert a timestamp string in the format "yyyy-MM-dd hh:mm:ss" (such as "2022-12-20 20:48:22")
   * to a unix timestamp, with milliseconds.
   *
   * @param timeString The timestamp string
   * @return Unix timestamp, with milliseconds
   */
  public static long getTimestampFor(String timeString) {
    try {
      Date parsedDate = dateFormat.parse(timeString);
      return parsedDate.getTime();
    } catch (ParseException e) {
      throw new RuntimeException("Invalid time string: " + timeString);
    }
  }
}
