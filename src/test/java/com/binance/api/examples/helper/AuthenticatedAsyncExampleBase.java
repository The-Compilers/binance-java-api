package com.binance.api.examples;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.security.ApiCredentials;

/**
 * An abstract base class which creates the necessary tools for an example requiring
 * authenticated endpoints, using asynchronous client
 */
public abstract class AuthenticatedAsyncExampleBase {
  protected final BinanceApiAsyncRestClient client;

  /**
   * Create an example, get API key and secret from the environment variables.
   */
  public AuthenticatedAsyncExampleBase() {
    ApiCredentials credentials = ApiCredentials.creatFromEnvironment();
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(credentials);
    client = factory.newAsyncRestClient();
  }
}
