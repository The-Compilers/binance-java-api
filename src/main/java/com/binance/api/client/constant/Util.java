package com.binance.api.client.constant;


import java.util.Arrays;
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

  public static final String BTC_TICKER = "BTC";

  private Util() {
  }

  /**
   * Check if the ticker is fiat currency.
   *
   * @return True if it is a fiat currency (USD, EUR, etc), false when it isn't
   */
  public static boolean isFiatCurrency(String symbol) {
    return FIAT_CURRENCIES.contains(symbol);
  }
}
