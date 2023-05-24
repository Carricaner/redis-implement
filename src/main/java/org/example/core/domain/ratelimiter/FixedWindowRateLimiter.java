package org.example.core.domain.ratelimiter;

import org.example.core.domain.ratelimiter.port.FixedBucketRateLimiterAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FixedWindowRateLimiter extends WindowRateLimiter {
    private final FixedBucketRateLimiterAdapter adapter;

    public FixedWindowRateLimiter(
            @Value("${server.rateLimiter.properties.limitCapacity:10}") long windowSize,
            @Value("${server.rateLimiter.properties.windowDuration:60}") long windowDuration,
            FixedBucketRateLimiterAdapter adapter) {
        super(windowSize, windowDuration);
        this.adapter = adapter;
    }

    @Override
    public boolean isAllowed(String clientId) {
        boolean result = false;
        Instant now = Instant.now();
        String key = getKeyName(clientId);
        long count = countWithinCurrentMinute(key, now);
        if (count < windowSize) {
            addOneRecord(key, now);
            result = true;
        }
        return result;
    }

    @Override
    public void reset() {
        // TODO
    }

    private String getKeyName(String clientId) {
        return WindowRateLimiter.KEY_PREFIX + clientId;
    }

    private long countWithinCurrentMinute(String key, Instant now) {
        return adapter.countBetween(key,
                now.truncatedTo(ChronoUnit.MINUTES).getEpochSecond(),
                now.plus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES).getEpochSecond());
    }

    private void addOneRecord(String key, Instant now) {
        adapter.plusOneVisit(key, now);
    }
}

