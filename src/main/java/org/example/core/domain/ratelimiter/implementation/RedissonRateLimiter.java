package org.example.core.domain.ratelimiter.implementation;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class RedissonRateLimiter {

  private final RedissonClient redissonClient;

  public RedissonRateLimiter(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
//    RRateLimiter limiter = redissonClient.getRateLimiter("myLimiter");
//    limiter.trySetRate(RateType.OVERALL, 5, 2, RateIntervalUnit.SECONDS);
  }
}
