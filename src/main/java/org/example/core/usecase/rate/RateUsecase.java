package org.example.core.usecase.rate;

public interface RateUsecase {
    boolean isAllowed(String clientId);
    void flushAllRecord(String clientId);
}