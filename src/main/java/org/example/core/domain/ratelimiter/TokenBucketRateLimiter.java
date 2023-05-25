package org.example.core.domain.ratelimiter;

import org.example.core.domain.ratelimiter.port.BucketRateLimiterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@Qualifier("token-bucket")
public class TokenBucketRateLimiter extends BucketRateLimiter {
    private final BucketRateLimiterAdapter bucketRateLimiterAdapter;
    private boolean initialized = false;

    @Autowired
    public TokenBucketRateLimiter(
            @Value("${server.rateLimiter.properties.limitCapacity:10}") long capacity,
            @Value("${server.rateLimiter.properties.refillRate:10}") long rate,
            BucketRateLimiterAdapter bucketRateLimiterAdapter) {
        super(capacity, rate);
        this.bucketRateLimiterAdapter = bucketRateLimiterAdapter;
    }

    private String getKey(String clientId) {
        return KEY_PREFIX + clientId;
    }

    void initialize(String key) {
        if (!initialized) {
            bucketRateLimiterAdapter.initializeBucket(key, capacity, Instant.now());
            initialized = true;
        }
    }

    @Override
    public boolean isAllowed(String clientId) {
        String key = getKey(clientId);
        initialize(key);
        Optional<TokenBucket> op = bucketRateLimiterAdapter.findBucketBucket(key);
        if (op.isEmpty()) {
            return false;
        }
        TokenBucket bucket = op.get();
        Instant currentTime = Instant.now();
        if (calculateTokens(bucket, currentTime) >= 0) {
            updateTokenInfo(key, bucket, currentTime);
            return true;
        }
        return false;
    }

    @Override
    public void reset(String clientId) {
        bucketRateLimiterAdapter.resetAllRecords(getKey(clientId));
    }

    private long calculateTokens(TokenBucket bucket, Instant currentTime) {
        long tokensToBeAdded = needRefill(currentTime.getEpochSecond(), bucket.updateTime()) ? rate : 0;
        return Math.min(capacity, bucket.tokensNumber() + tokensToBeAdded) - TOKEN_NUMBER_TO_BE_CONSUMED;
    }

    private void updateTokenInfo(String key, TokenBucket bucket, Instant currentTime) {
        long newTokenNumber = calculateTokens(bucket, currentTime);
        long newUpdateTime = needRefill(currentTime.getEpochSecond(), bucket.updateTime()) ?
                currentTime.getEpochSecond() : bucket.updateTime();
        TokenBucket updatedBucket = new TokenBucket(newTokenNumber, newUpdateTime);
        bucketRateLimiterAdapter.updateBucketInfo(key, updatedBucket);
    }

    private boolean needRefill(long currentTime, long lastRefillTime) {
        return (currentTime - lastRefillTime) / 60 > 0;
    }
}
