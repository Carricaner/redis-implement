package org.example.core.domain.ratelimiter;

import java.time.Instant;

public interface RateLimiter {
    boolean tryAcquire(String clientId, Instant time);
    void refreshAll(String clientId);
}
