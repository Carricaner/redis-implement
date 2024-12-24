package org.example.core.domain.ratelimiter;

import java.time.Instant;

public interface RateLimiter {
    boolean isAllowed(String clientId, Instant time);
    void refreshAll(String clientId);
}
