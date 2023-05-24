package org.example.core.usecase.rate;

import org.example.core.domain.ratelimiter.WindowRateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateUsecaseImpl implements RateUsecase {
    private final WindowRateLimiter windowRateLimiter;

    public RateUsecaseImpl(WindowRateLimiter windowRateLimiter) {
        this.windowRateLimiter = windowRateLimiter;
    }

    @Override
    public boolean isAllowed(String clientId) {
        return windowRateLimiter.isAllowed(clientId);
    }

    @Override
    public void flushAllRecord(String clientId) {
        windowRateLimiter.reset(clientId);
    }
}
