package org.example.core.domain.ratelimiter;

public interface RateLimiter {
    boolean isAllowed(String clientId);
}