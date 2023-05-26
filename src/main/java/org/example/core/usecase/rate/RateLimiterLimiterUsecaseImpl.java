package org.example.core.usecase.rate;

import org.example.core.domain.ratelimiter.BucketRateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterLimiterUsecaseImpl implements RateLimiterUsecase {
    private final BucketRateLimiter bucketRateLimiter;

    public RateLimiterLimiterUsecaseImpl(
            @Qualifier("leaky-bucket") BucketRateLimiter bucketRateLimiter) {
        this.bucketRateLimiter = bucketRateLimiter;
    }

    @Override
    public boolean isAllowed(String clientId) {
        return bucketRateLimiter.isAllowed(clientId);
    }

    @Override
    public void flushAllRecord(String clientId) {
        bucketRateLimiter.reset(clientId);
    }
}
