package org.example.core.usecase.ratelimiter;

import org.example.core.domain.ratelimiter.RateLimiterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterLimiterUsecaseImpl implements RateLimiterUsecase {
  private final RateLimiterManager rateLimiterManager;

  @Autowired
  public RateLimiterLimiterUsecaseImpl(RateLimiterManager rateLimiterManager) {
    this.rateLimiterManager = rateLimiterManager;
  }

  @Override
  public void flushAllRecord(String clientId) {
    rateLimiterManager.getRateLimiter().refreshAll(clientId);
  }
}
