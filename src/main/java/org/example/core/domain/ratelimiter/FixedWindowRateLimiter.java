package org.example.core.domain.ratelimiter;

import org.example.core.domain.ratelimiter.port.WindowRateLimiterAdapter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class FixedWindowRateLimiter extends WindowRateLimiter {
    private final WindowRateLimiterAdapter adapter;

    public FixedWindowRateLimiter(long windowSize, long windowDuration, WindowRateLimiterAdapter adapter) {
        super(windowSize, windowDuration);
        this.adapter = adapter;
    }

    @Override
    public boolean isAllowed(String clientId, Instant time) {
        boolean result = false;
        String key = getKeyName(clientId);
        long count = countWithinCurrentMinute(key, time);
        if (count < windowSize) {
            addOneRecord(key, time);
            result = true;
        }
        return result;
    }

    @Override
    public void refreshAll(String clientId) {
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

