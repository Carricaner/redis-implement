package org.example.core.domain.ratelimiter;

import java.time.Instant;
import java.util.Optional;
import org.example.core.domain.ratelimiter.component.TokenBucket;
import org.example.core.domain.ratelimiter.port.BucketRateLimiterAdapter;

public class LeakyBucketRateLimiter extends BucketRateLimiter {
    private final BucketRateLimiterAdapter bucketRateLimiterAdapter;

    public LeakyBucketRateLimiter(long capacity, long rate, BucketRateLimiterAdapter bucketRateLimiterAdapter) {
        super(capacity, rate);
        this.bucketRateLimiterAdapter = bucketRateLimiterAdapter;
    }

    private String getKey(String clientId) {
        return KEY_PREFIX + clientId;
    }

    void createIfAbsent(String key) {
        bucketRateLimiterAdapter.initializeBucket(key, 0L, Instant.now());
    }

    @Override
    public boolean isAllowed(String clientId, Instant time) {
        String key = getKey(clientId);
        createIfAbsent(key);
        Optional<TokenBucket> op = bucketRateLimiterAdapter.findTokenBucket(key);
        if (op.isEmpty()) {
            return false;
        }
        TokenBucket bucket = op.get();
        if (calculateTokens(bucket, time) <= capacity) {
            updateTokenInfo(key, bucket, time);
            return true;
        }
        return false;
    }

    @Override
    public void refreshAll(String clientId) {
        bucketRateLimiterAdapter.resetAllRecords(getKey(clientId));
    }

    private long calculateTokens(TokenBucket bucket, Instant currentTime) {
        long tokensToBeLeaked = needLeaking(currentTime.getEpochSecond(), bucket.updateTime()) ? rate : 0;
        return Math.max(0, bucket.tokensNumber() - tokensToBeLeaked) + TOKEN_NUMBER_TO_BE_ADDED;
    }

    private void updateTokenInfo(String key, TokenBucket bucket, Instant currentTime) {
        long newTokenNumber = calculateTokens(bucket, currentTime);
        long newUpdateTime = needLeaking(currentTime.getEpochSecond(), bucket.updateTime()) ?
                currentTime.getEpochSecond() : bucket.updateTime();
        TokenBucket updatedBucket = new TokenBucket(newTokenNumber, newUpdateTime);
        bucketRateLimiterAdapter.updateBucketInfo(key, updatedBucket);
    }

    private boolean needLeaking(long currentTime, long lastLeakingTime) {
        return (currentTime - lastLeakingTime) / 60 > 0;
    }
}
