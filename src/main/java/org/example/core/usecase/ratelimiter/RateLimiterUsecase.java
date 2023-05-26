package org.example.core.usecase.ratelimiter;

public interface RateLimiterUsecase {
  boolean isAllowed(String clientId);

  void flushAllRecord(String clientId);
}
