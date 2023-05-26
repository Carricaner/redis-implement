package org.example.core.usecase.ratelimiter;

import org.example.core.domain.ratelimiter.RateLimiterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RateLimiterLimiterUsecaseImpl implements RateLimiterUsecase {
  private final RateLimiterClient rateLimiterClient;

  @Autowired
  public RateLimiterLimiterUsecaseImpl(RateLimiterClient rateLimiterClient) {
    this.rateLimiterClient = rateLimiterClient;
  }

  @Override
  public boolean isAllowed(String clientId) {
    return rateLimiterClient.getRateLimiter().isAllowed(clientId, Instant.now());
  }

  @Override
  public void flushAllRecord(String clientId) {
    rateLimiterClient.getRateLimiter().refreshAll(clientId);
  }
}
