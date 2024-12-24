package org.example.core.domain.ratelimiter.error;

public class RateLimitExceededException extends RuntimeException {
  public static final String DEFAULT_MESSAGE = "Exceed the rate limit.";
  public RateLimitExceededException() {
    super(DEFAULT_MESSAGE);
  }
}
