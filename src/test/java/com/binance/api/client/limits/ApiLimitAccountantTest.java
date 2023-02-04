package com.binance.api.client.limits;

import org.junit.Test;
import static com.binance.api.client.limits.LimitTestingUtils.getTime;
import static org.junit.Assert.assertEquals;

/**
 * Tests for ApiLimitAccountant
 */
public class ApiLimitAccountantTest {
  /**
   * One call, no limits reached
   */
  @Test
  public void testNoLimitReached() {
    long t = getTime();
    resetAccountantToOneIpCall(t - 1000, 100);
    assertIpSleepTime(0, 100, t);
  }

  /**
   * One call, limit reached, sleep
   */
  @Test
  public void testOneCallLimitReached() {
    long t = getTime();
    resetAccountantToOneIpCall(t - 1000, 1200);
    assertIpSleepTime(59 * 1000, 100, t);
  }

  /**
   * Three calls, limit reached, sleep. Then forget the first one, no sleep.
   */
  @Test
  public void testThreeCallsLimitReached() {
    long t = getTime();
    resetAccountantToThreeIpCalls(t - 30000, 400, t - 20000, 400, t - 10000, 400);
    assertIpSleepTime(30 * 1000, 200, t);
    assertIpSleepTime(40 * 1000, 500, t);
    ApiLimitAccountant accountant = ApiLimitAccountant.getInstance();
    accountant.forgetOldApiCalls(t + 35 * 1000);
    assertIpSleepTime(0, 200, t + 35 * 1000);
  }

  /**
   * Three calls, 5-minute limit reached, sleep.
   */
  @Test
  public void testThreeCallsFiveMinuteLimitReached() {
    long t = getTime();
    resetAccountantToThreeIpCalls(t - 4 * 60 * 1000, 6000, t - 20000, 400, t - 10000, 400);
    assertIpSleepTime(60 * 1000, 200, t);
  }

  /**
   * Three calls, limit reached, sleep. Then forget the first one, sleep reduced.
   * Forget the second one, no sleep anymore.
   */
  @Test
  public void testForgettingFirstReducesSleep() {
    long t = getTime();
    resetAccountantToThreeIpCalls(t - 30000, 100, t - 20000, 500, t - 10000, 500);
    assertIpSleepTime(30 * 1000, 200, t);
    ApiLimitAccountant accountant = ApiLimitAccountant.getInstance();
    accountant.forgetOldApiCalls(t + 35 * 1000);
    assertIpSleepTime(5 * 1000, 300, t + 35 * 1000);
  }

  /**
   * One call with IP weight and UID weight, UID limit reached (no IP limit reached), sleep.
   */
  @Test
  public void testIpAndUidWeightUidReached() {
    long t = getTime();
    resetAccountantToSapiIpAndUidCall(t - 1000, 10000, 180000);
    assertSapiIpUidSleepTime(59 * 1000, 200, t);
  }

  /**
   * One call with IP weight and UID weight, IP limit reached (no UID limit reached), sleep.
   */
  @Test
  public void testIpAndUidWeightIpReached() {
    long t = getTime();
    resetAccountantToSapiIpAndUidCall(t - 1000, 12000, 10000);
    assertSapiIpUidSleepTime(59 * 1000, 200, t);
  }


  private void assertIpSleepTime(int expectedSleepTime, int newApiCallWeight, long timestamp) {
    ApiLimitAccountant accountant = ApiLimitAccountant.getInstance();
    long sleep = accountant.getMinSleepDuration(createIpWeight(newApiCallWeight), timestamp);
    assertEquals(expectedSleepTime, sleep);
  }

  private void assertSapiIpUidSleepTime(int expectedSleepTime, int newApiCallWeight, long timestamp) {
    ApiLimitAccountant accountant = ApiLimitAccountant.getInstance();
    long sleep = accountant.getMinSleepDuration(createSapiIpUidWeight(newApiCallWeight), timestamp);
    assertEquals(expectedSleepTime, sleep);
  }

  private void resetAccountantToOneIpCall(long time, int weight) {
    resetAccountant(new long[]{time}, new int[]{weight}, new ApiLimitType[]{ApiLimitType.API_IP});
  }

  private void resetAccountantToThreeIpCalls(long time1, int weight1, long time2, int weight2,
                                             long time3, int weight3) {
    resetAccountant(new long[]{time1, time2, time3}, new int[]{weight1, weight2, weight3},
        new ApiLimitType[]{ApiLimitType.API_IP, ApiLimitType.API_IP, ApiLimitType.API_IP});
  }

  private void resetAccountantToSapiIpAndUidCall(long time, int ipWeight, int uidWeight) {
    resetAccountant(new long[]{time, time}, new int[]{ipWeight, uidWeight},
        new ApiLimitType[]{ApiLimitType.SAPI_IP, ApiLimitType.SAPI_UID});

  }

  private void resetAccountant(long[] apiCallTimes, int[] apiCallWeights,
                               ApiLimitType[] apiCallTypes) {
    assert apiCallTimes.length == apiCallWeights.length;
    assert apiCallTimes.length == apiCallTypes.length;

    ApiLimitAccountant accountant = ApiLimitAccountant.getInstance();
    accountant.forgetAllApiCalls();

    for (int i = 0; i < apiCallTimes.length; ++i) {
      ApiCallWeights weights = new ApiCallWeights().setWeight(apiCallTypes[i], apiCallWeights[i]);
      accountant.addUsedWeights(weights, apiCallTimes[i]);
    }
  }


  private ApiCallWeights createIpWeight(int weight) {
    return new ApiCallWeights().setWeight(ApiLimitType.API_IP, weight);
  }
  private ApiCallWeights createSapiIpUidWeight(int weight) {
    return new ApiCallWeights()
        .setWeight(ApiLimitType.SAPI_IP, weight)
        .setWeight(ApiLimitType.SAPI_UID, weight);
  }
}
