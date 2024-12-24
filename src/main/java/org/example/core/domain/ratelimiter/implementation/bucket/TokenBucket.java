package org.example.core.domain.ratelimiter.implementation.bucket;

public record TokenBucket(long tokensNumber, long updateTime) {

}
