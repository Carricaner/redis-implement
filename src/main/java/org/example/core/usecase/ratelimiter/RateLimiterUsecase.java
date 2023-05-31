package org.example.core.usecase.ratelimiter;

public interface RateLimiterUsecase {

  void flushAllRecord(String clientId);
}
