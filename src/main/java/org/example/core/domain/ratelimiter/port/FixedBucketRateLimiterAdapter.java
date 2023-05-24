package org.example.core.domain.ratelimiter.port;

import java.time.Instant;

public interface FixedBucketRateLimiterAdapter {
    long countBetween(String key, long start, long end);
    void plusOneVisit(String key, Instant current);

    default long countBetween(String key, long start) {
        return countBetween(key, start, Instant.now().getEpochSecond());
    }
}
