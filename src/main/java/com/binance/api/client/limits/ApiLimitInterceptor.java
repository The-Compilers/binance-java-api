package com.binance.api.client.limits;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import static com.binance.api.client.limits.ApiLimitType.*;
import static com.binance.api.client.limits.ApiWeightHeaders.*;

/**
 * Makes sure that the requests sent to Binance API are within the allowed rate limits,
 * to avoid bans.
 */
public class ApiLimitInterceptor implements Interceptor {
  private static final Logger logger = Logger.getLogger(ApiLimitInterceptor.class);


  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {
    ApiLimitAccountant accountant = ApiLimitAccountant.getInstance();

    long timeNow = System.currentTimeMillis();
    accountant.forgetOldApiCalls(timeNow);
    ApiCallWeights weights = getApiCallWeights(chain.request());
    sleepIfApiLimitsExceeded(weights);
    accountant.addUsedWeights(weights, timeNow);

    Response response = chain.proceed(chain.request());
    debugPrintUsedWeightHeaders(response);

    return response;
  }

  private static void debugPrintUsedWeightHeaders(Response response) {
    for (String headerName : response.headers().names()) {
      if (headerName.startsWith("x-mbx-used-weight") || headerName.startsWith("x-mbx-order-count")
          || headerName.startsWith("x-sapi-used")) {
        logger.info("  -> " +  headerName + ": " + response.header(headerName));
      }
    }
  }

  private ApiCallWeights getApiCallWeights(Request request) {
    ApiCallWeights weights = new ApiCallWeights();
    weights.setWeight(API_IP, getWeightFromHeader(request.header(API_WEIGHT_PER_IP_KEY)))
        .setWeight(ORDER, getWeightFromHeader(request.header(API_WEIGHT_PER_ORDER)))
        .setWeight(SAPI_IP, getWeightFromHeader(request.header(SAPI_WEIGHT_PER_IP_KEY)))
        .setWeight(SAPI_UID, getWeightFromHeader(request.header(SAPI_WEIGHT_PER_UID_KEY)));
    return weights;
  }

  private Integer getWeightFromHeader(String headerValue) {
    Integer value = null;
    if (headerValue != null) {
      try {
        value = Integer.parseInt(headerValue);
      } catch (NumberFormatException e) {
        logger.info("Invalid API weight header value: " + headerValue);
      }
    }
    return value;
  }

  private void sleepIfApiLimitsExceeded(ApiCallWeights weights) {
    long timestamp = System.currentTimeMillis();
    long minSleepTime = ApiLimitAccountant.getInstance().getMinSleepDuration(weights, timestamp);
    if (minSleepTime > 0) {
      sleep(minSleepTime);
    }
  }

  /**
   * Sleep the specified duration.
   *
   * @param duration Sleep duration, in milliseconds.
   */
  private void sleep(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
