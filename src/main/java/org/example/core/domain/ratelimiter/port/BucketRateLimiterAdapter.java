package org.example.core.domain.ratelimiter.port;

import org.example.core.domain.ratelimiter.TokenBucket;

import java.time.Instant;
import java.util.Optional;

public interface BucketRateLimiterAdapter {
    void initializeBucket(String key, long capacity, Instant time);
    Optional<TokenBucket> findBucketBucket(String key);
    void updateBucketInfo(String key, TokenBucket updatedBucket);

    default String getTokensFieldNameOfBucket() {
        return "tokens";
    }

    default String getUpdateTimeFieldNameOfBucket() {
        return "updateAt";
    }
}