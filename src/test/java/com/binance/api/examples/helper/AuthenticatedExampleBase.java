package com.binance.api.examples;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.security.ApiCredentials;

/**
 * An abstract base class which creates the necessary tools for an example requiring
 * authenticated endpoints.
 */
public abstract class AuthenticatedExampleBase {
  protected final BinanceApiRestClient client;

  /**
   * Create new example with authenticated endpoints.
   *
   * @param useHttpLogging When true, debug-print all HTTP requests and responses
   */
  public AuthenticatedExampleBase(boolean useHttpLogging) {
    client = createClient(useHttpLogging);
  }

  /**
   * Create an example, get API key and secret from the environment variables.
   * Disable debugging by default
   */
  public AuthenticatedExampleBase() {
    client = createClient(false);
  }

  private BinanceApiRestClient createClient(boolean useHttpLogging) {
    ApiCredentials credentials = ApiCredentials.creatFromEnvironment();
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(credentials);
    BinanceApiConfig.useHttpLogging = useHttpLogging;
    return factory.newRestClient();
  }
}
