package org.example.core.usecase.rate;

public interface RateLimiterUsecase {
    boolean isAllowed(String clientId);
    void flushAllRecord(String clientId);
}
