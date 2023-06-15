package org.example.core.domain.ratelimiter;

import org.example.core.configuration.ratelimiter.RateLimiterPropertiesConfig;
import org.example.core.domain.ratelimiter.port.BucketRateLimiterAdapter;
import org.example.core.domain.ratelimiter.port.WindowRateLimiterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterClient {
    private RateLimiter rateLimiter;
    private final WindowRateLimiterAdapter windowRateLimiterAdapter;
    private final BucketRateLimiterAdapter bucketRateLimiterAdapter;

    @Autowired
    public RateLimiterClient(
            RateLimiterPropertiesConfig config,
            WindowRateLimiterAdapter windowRateLimiterAdapter,
            BucketRateLimiterAdapter bucketRateLimiterAdapter) {
        this.windowRateLimiterAdapter = windowRateLimiterAdapter;
        this.bucketRateLimiterAdapter = bucketRateLimiterAdapter;
        init(config);
    }

    private void init(RateLimiterPropertiesConfig config) {
        rateLimiter = generateRateLimiter(config);
    }

    private RateLimiter generateRateLimiter(RateLimiterPropertiesConfig config) {
        return switch(config.getType()) {
            case RateLimiterPropertiesConfig.TYPE_SLIDING_WINDOW ->
                    new SlidingWindowRateLimiter(
                            config.getLimitCapacity(), config.getWindowDuration(), windowRateLimiterAdapter);
            case RateLimiterPropertiesConfig.TYPE_FIXED_WINDOW ->
                    new FixedWindowRateLimiter(
                            config.getLimitCapacity(), config.getWindowDuration(), windowRateLimiterAdapter);
            case RateLimiterPropertiesConfig.TYPE_TOKEN_BUCKET ->
                    new TokenBucketRateLimiter(
                            config.getLimitCapacity(), config.getRefillRate(), bucketRateLimiterAdapter);
            case RateLimiterPropertiesConfig.TYPE_LEAKY_BUCKET ->
                    new LeakyBucketRateLimiter(
                            config.getLimitCapacity(), config.getLeakingRate(), bucketRateLimiterAdapter);
            default -> throw new RuntimeException("Unrecognized rate limiter type: " + config.getType());
        };
    }

    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }
}
