package org.example.core.domain.ratelimiter;

import org.example.core.configuration.RateLimiterPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterClient {
    private RateLimiter rateLimiter;

    @Autowired
    public RateLimiterClient(RateLimiterPropertiesConfig rateLimiterPropertiesConfig) {
        System.out.println("Here");
    }
}
