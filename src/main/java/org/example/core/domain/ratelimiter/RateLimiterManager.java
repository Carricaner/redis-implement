package org.example.core.domain.ratelimiter;

import org.example.core.configuration.ratelimiter.RateLimiterConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterManager {
  private final RateLimiter rateLimiter;

  @Autowired
  public RateLimiterManager(
      RateLimiterConfigProperties properties, RateLimiterFactory rateLimiterFactory) {
    this.rateLimiter = rateLimiterFactory.generateRateLimiter(properties.getType());
  }

  public RateLimiter getRateLimiter() {
    return rateLimiter;
  }
}
