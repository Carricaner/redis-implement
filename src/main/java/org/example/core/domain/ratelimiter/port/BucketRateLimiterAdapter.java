package org.example.core.domain.ratelimiter.port;

import java.time.Instant;
import java.util.Optional;
import org.example.core.domain.ratelimiter.component.TokenBucket;

public interface BucketRateLimiterAdapter {
    void initializeBucket(String key, long capacity, Instant time);

    Optional<TokenBucket> findTokenBucket(String key);

    void updateBucketInfo(String key, TokenBucket updatedBucket);

    void resetAllRecords(String key);

    default String getTokensFieldNameOfBucket() {
        return "tokens";
    }

    default String getUpdateTimeFieldNameOfBucket() {
        return "updateAt";
    }
}
