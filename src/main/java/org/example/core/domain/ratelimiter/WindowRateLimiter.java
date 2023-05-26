package org.example.core.domain.ratelimiter;

import java.time.Instant;

public abstract class WindowRateLimiter implements RateLimiter {
    public static final String KEY_PREFIX = "window-rate-";
    protected final long windowSize;
    protected final long windowDuration;

    public WindowRateLimiter(long windowSize, long windowDuration) {
        this.windowSize = windowSize;
        this.windowDuration = windowDuration;
    }

    @Override
    public boolean isAllowed(String clientId, Instant time) {
        return false;
    }

    public void refreshAll(String clientId) {}
}
