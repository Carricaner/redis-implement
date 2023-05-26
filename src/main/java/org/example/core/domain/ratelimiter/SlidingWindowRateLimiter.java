package org.example.core.domain.ratelimiter;

import org.example.core.domain.ratelimiter.port.WindowRateLimiterAdapter;

import java.time.Instant;

public class SlidingWindowRateLimiter extends WindowRateLimiter {

    private final WindowRateLimiterAdapter adapter;

    public SlidingWindowRateLimiter(long windowSize, long windowDuration, WindowRateLimiterAdapter adapter) {
        super(windowSize, windowDuration);
        this.adapter = adapter;
    }

    @Override
    public boolean isAllowed(String clientId, Instant time) {
        boolean result = false;
        String key = getKeyName(clientId);
        if (!exceedLimit(key, time)) {
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
        return KEY_PREFIX + clientId;
    }

    private boolean exceedLimit(String key, Instant current) {
        return adapter.countBetween(key, current.getEpochSecond() - windowDuration, current.getEpochSecond()) >= windowSize ;
    }

    private void addOneRecord(String key, Instant currentTime) {
        adapter.plusOneVisit(key, currentTime);
    }
}
