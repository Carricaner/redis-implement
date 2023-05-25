package org.example.core.domain.ratelimiter;

import org.example.core.domain.ratelimiter.port.WindowRateLimiterAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FixedWindowRateLimiter extends WindowRateLimiter {
    private final WindowRateLimiterAdapter adapter;

    public FixedWindowRateLimiter(
            @Value("10") long windowSize,
            @Value("60") long windowDuration,
            WindowRateLimiterAdapter adapter) {
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
    public void reset(String clientId) {
        adapter.resetAllRecords(getKeyName(clientId));
    }

    private String getKeyName(String clientId) {
        return WindowRateLimiter.KEY_PREFIX + clientId;
    }

    private long countWithinCurrentMinute(String key, Instant now) {
        return adapter.countBetween(key,
                now.truncatedTo(ChronoUnit.MINUTES).getEpochSecond(),
                now.plus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES).getEpochSecond());
    }

    private void addOneRecord(String key, Instant currentTime) {
        adapter.plusOneVisit(key, currentTime);
    }
}

