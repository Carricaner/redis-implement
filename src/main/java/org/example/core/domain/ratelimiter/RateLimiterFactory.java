package org.example.core.domain.ratelimiter;

import org.example.core.configuration.ratelimiter.RateLimiterConfigProperties.RateLimiterType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterFactory {

  private final ApplicationContext applicationContext;

  public RateLimiterFactory(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public RateLimiter generateRateLimiter(RateLimiterType rateLimiterType) {
    return switch (rateLimiterType) {
      case FIXED_WINDOW -> applicationContext.getBean("fixedWindowRateLimiter", RateLimiter.class);
      case SLIDING_WINDOW -> applicationContext.getBean(
          "slidingWindowRateLimiter", RateLimiter.class);
      case TOKEN_BUCKET -> applicationContext.getBean("tokenBucketRateLimiter", RateLimiter.class);
      case LEAKY_BUCKET -> applicationContext.getBean("leakyBucketRateLimiter", RateLimiter.class);
      case REDISSON -> applicationContext.getBean("redissonRateLimiter", RateLimiter.class);
    };
  }
}
