package org.example.core.domain.ratelimiter;

public abstract class BucketRateLimiter implements RateLimiter {
    public static final String KEY_PREFIX = "bucket-rate-";
    protected static final long TOKEN_NUMBER_TO_BE_CONSUMED = 1L;
    protected static final int TOKEN_NUMBER_TO_BE_ADDED = 1;
    protected final long capacity;
    protected final long rate;

    public BucketRateLimiter(long capacity, long rate) {
        this.capacity = capacity;
        this.rate = rate;
    }

    @Override
    public boolean isAllowed(String clientId) {
        return false;
    }

    @Override
    public void refreshAll(String clientId) {}
}
