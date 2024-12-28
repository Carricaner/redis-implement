package org.example.core.configuration.web;

import java.time.Instant;
import org.redisson.api.RRingBuffer;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
public class ApiMetricsService {
  public static final String API_METRICS_RING_BUFFER_NAME = "api-metrics-buffer";
  private final RRingBuffer<ApiCall> ringBuffer;

  public ApiMetricsService(RedissonClient redissonClient) {
    // Initialize a Ring Buffer with a capacity of 1,000 entries
    this.ringBuffer = redissonClient.getRingBuffer(API_METRICS_RING_BUFFER_NAME);
    this.ringBuffer.trySetCapacity(100);
  }

  public void addApiCall(String endpoint, long responseTimeMs) {
    ApiCall apiCall = new ApiCall(endpoint, Instant.now(), responseTimeMs);
    ringBuffer.add(apiCall);
  }

  public Iterable<ApiCall> getRecentApiCalls() {
    return ringBuffer;
  }
}
