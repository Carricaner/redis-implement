package org.example.core.usecase.rate;

import org.example.core.domain.ratelimiter.BucketRateLimiter;
import org.example.core.domain.ratelimiter.WindowRateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RateUsecaseImpl implements RateUsecase {
    private final WindowRateLimiter windowRateLimiter;
    private final BucketRateLimiter bucketRateLimiter;

    public RateUsecaseImpl(
            @Qualifier("sliding-window") WindowRateLimiter windowRateLimiter,
            @Qualifier("leaky-bucket") BucketRateLimiter bucketRateLimiter) {
        this.windowRateLimiter = windowRateLimiter;
        this.bucketRateLimiter = bucketRateLimiter;
    }

    @Override
    public boolean isAllowed(String clientId) {
        return bucketRateLimiter.isAllowed(clientId);
//        return windowRateLimiter.isAllowed(clientId);
    }

    @Override
    public void flushAllRecord(String clientId) {
        bucketRateLimiter.reset(clientId);
//        windowRateLimiter.reset(clientId);
    }
}
