package org.example.core.domain.ratelimiter.component;

public record TokenBucket(long tokensNumber, long updateTime) {

}
