package org.example.core.domain.ratelimiter.implementation;

import java.time.Instant;
import org.example.core.configuration.ratelimiter.RateLimiterConfigProperties;
import org.example.core.domain.ratelimiter.RateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class RedissonRateLimiter implements RateLimiter {
  private final RedissonClient redissonClient;
  private final RateLimiterConfigProperties properties;

  public RedissonRateLimiter(
      RateLimiterConfigProperties properties, RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
    this.properties = properties;
  }

  @Override
  public boolean tryAcquire(String clientId, Instant time) {
    RRateLimiter rateLimiter = redissonClient.getRateLimiter(clientId);
    rateLimiter.trySetRate(
        RateType.PER_CLIENT,
        properties.getLimitCapacity(),
        properties.getWindowDuration(),
        RateIntervalUnit.SECONDS);
    return rateLimiter.tryAcquire();
  }

  @Override
  public void refreshAll(String clientId) {
    redissonClient.getRateLimiter(clientId).delete();
  }
}
