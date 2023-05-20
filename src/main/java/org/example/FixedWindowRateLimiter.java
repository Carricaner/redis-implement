package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FixedWindowRateLimiter {
    private final int limit;
    private final int windowDuration; // in seconds
    private final Jedis jedis;

    @Autowired
    public FixedWindowRateLimiter(
            @Value("10") int limit,
            @Value("60") int windowDuration,
            Jedis jedis) {
        this.limit = limit;
        this.windowDuration = windowDuration;
        this.jedis = jedis;
    }

    public boolean isAllowed(String clientId) {
        long currentTime = Instant.now()
                .getEpochSecond();

        long startOfWindow = Instant.ofEpochSecond(currentTime)
                .truncatedTo(ChronoUnit.MINUTES).getEpochSecond();

        removeExpiredProactively(clientId, startOfWindow);

        long requestCount = getRequestCount(clientId);

        if (requestCount < limit) {
            jedis.zadd(clientId, currentTime, String.valueOf(currentTime));
            return true;
        }

        return false;
    }

    private void removeExpiredProactively(String clientId, long startOfCurrentWindow) {
        jedis.zremrangeByScore(clientId, 0, startOfCurrentWindow);
    }

    private long getRequestCount(String clientId) {
        return jedis.zcard(clientId);
    }

    public int getLimit() {
        return limit;
    }

    public int getWindowDuration() {
        return windowDuration;
    }
}

