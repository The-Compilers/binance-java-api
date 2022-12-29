package com.binance.api.examples;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.security.ApiCredentials;

/**
 * An abstract base class which creates the necessary tools for an example requiring
 * authenticated endpoints.
 */
public abstract class AuthenticatedExampleBase {
  protected final BinanceApiRestClient client;

  /**
   * Create an example, get API key and secret from the environment variables.
   */
  public AuthenticatedExampleBase() {
    ApiCredentials credentials = ApiCredentials.creatFromEnvironment();
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(credentials);
    client = factory.newRestClient();
  }
}
