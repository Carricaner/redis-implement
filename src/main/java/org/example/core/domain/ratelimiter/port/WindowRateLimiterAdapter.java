package org.example.core.domain.ratelimiter.port;

import java.time.Instant;

public interface WindowRateLimiterAdapter {
    long countBetween(String key, long start, long end);
    void plusOneVisit(String key, Instant current);
    void resetAllRecords(String key);
}
