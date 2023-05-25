package org.example.core.domain.ratelimiter;

public record TokenBucket(
        long tokensNumber,
        long updateTime
) {


}
