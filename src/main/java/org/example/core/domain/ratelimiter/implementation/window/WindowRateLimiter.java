package org.example.core.domain.ratelimiter.implementation.window;

import java.time.Instant;
import org.example.core.domain.ratelimiter.RateLimiter;

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
}
