package com.binance.api.client.limits;

import org.junit.Test;
import static com.binance.api.client.limits.LimitTestingUtils.getTime;
import static org.junit.Assert.assertEquals;

/**
 * Tests for ApiCallBucket
 */
public class ApiCallBucketTest {
  @Test
  public void testAll() {
    ApiCallBucket bucket = new ApiCallBucket(60, 10000);
    assertEquals(0, bucket.getUsedWeight());
    assertEquals(60, bucket.getMaxWeight());

    long t = getTime();
    bucket.addCall(new ApiCallEntry(t, 10));
    assertEquals(10, bucket.getUsedWeight());
    bucket.addCall(new ApiCallEntry(t + 1000, 12));
    assertEquals(22, bucket.getUsedWeight());
    bucket.addCall(new ApiCallEntry(t + 2000, 30));
    assertEquals(52, bucket.getUsedWeight());
    assertEquals(0, bucket.getMinSleepDuration(t + 2000, 4));
    assertEquals(8000, bucket.getMinSleepDuration(t + 2000, 10));

    bucket.forgetOldApiCalls(t + 10200);
    assertEquals(42, bucket.getUsedWeight());

    bucket.addCall(new ApiCallEntry(t + 3000, 10));
    assertEquals(52, bucket.getUsedWeight());
    bucket.addCall(new ApiCallEntry(t + 4000, 20));
    assertEquals(72, bucket.getUsedWeight());

    bucket.forgetOldApiCalls(t + 13200);
    assertEquals(20, bucket.getUsedWeight());

    bucket.forgetOldApiCalls(t + 14200);
    assertEquals(0, bucket.getUsedWeight());
  }
}
