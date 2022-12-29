package com.binance.api.client.security;

/**
 * Stores Binance API key and API secret.
 */
public class ApiCredentials {
  private static final String ENV_VAR_KEY = "BINANCE_API_KEY";
  private static final String ENV_VAR_SECRET = "BINANCE_API_SECRET";

  private final String key;
  private final String secret;


  /**
   * Create a new Binance API credential pair.
   *
   * @param key    API key
   * @param secret API secret
   */
  public ApiCredentials(String key, String secret) {
    this.key = key;
    this.secret = secret;
  }

  /**
   * Creates credentials from environment variables (see ENV_VAR_xxx constante).
   *
   * @return The credential object
   * @throws IllegalArgumentException When one of the environment variables is missing
   */
  public static ApiCredentials creatFromEnvironment() throws IllegalArgumentException {
    String apiKey = System.getenv(ENV_VAR_KEY);
    if (apiKey == null) {
      throw new IllegalArgumentException(ENV_VAR_KEY + " environment variable is missing");
    }
    String apiSecret = System.getenv(ENV_VAR_SECRET);
    if (apiSecret == null) {
      throw new IllegalArgumentException(ENV_VAR_SECRET + " environment variable is missing");
    }
    return new ApiCredentials(apiKey, apiSecret);
  }

  public String getKey() {
    return key;
  }

  public String getSecret() {
    return secret;
  }
}
