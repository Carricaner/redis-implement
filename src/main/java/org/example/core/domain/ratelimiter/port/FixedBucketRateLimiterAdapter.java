package org.example.core.domain.ratelimiter.port;

import java.time.Instant;

public interface FixedBucketRateLimiterAdapter {
    long countBetween(String key, long start, long end);
    void plusOneVisit(String key, Instant current);
    void resetAllRecords(String key);
    default long countBetween(String key, long start) {
        return countBetween(key, start, Instant.now().getEpochSecond());
    }
}
