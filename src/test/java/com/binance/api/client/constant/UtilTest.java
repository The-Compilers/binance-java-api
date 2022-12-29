package com.binance.api.client.constant;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @see Util
 */
public class UtilTest {
  @Test
  public void testFiatCheck() {
    assertTrue(Util.isFiatCurrency("USDT"));
    assertTrue(Util.isFiatCurrency("BUSD"));
    assertTrue(Util.isFiatCurrency("EUR"));
    assertFalse(Util.isFiatCurrency("BTC"));
    assertFalse(Util.isFiatCurrency("ETH"));
  }
}
