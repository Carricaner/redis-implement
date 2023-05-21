package org.example.ratelimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.Instant;

@Component
@Qualifier("slidingWindow")
public class SlidingWindowRateLimiter implements RateLimiter {

    private static final String KEY_NAME = "rate";
    private final int limit;
    private final int windowDuration; // in seconds
    private final Jedis jedis;

    @Autowired
    public SlidingWindowRateLimiter(
            @Value("10") int limit,
            @Value("60") int windowDuration,
            Jedis jedis) {
        this.limit = limit;
        this.windowDuration = windowDuration;
        this.jedis = jedis;
    }

    @Override
    public boolean isAllowed(String clientId) {
        boolean result = false;
        long currentTime = Instant.now().getEpochSecond();
        long windowStart = currentTime - windowDuration;
        long count = getCount(clientId);
        removeRecordByScore(clientId, windowStart);
        if (count < limit) {
            jedis.zadd(clientId, currentTime, String.valueOf(currentTime));
            jedis.expire(clientId, windowDuration);
            result = true;
        }
        return result;
    }

    private void removeRecordByScore(String clientId, long windowStart) {
        jedis.zremrangeByScore(clientId, 0, windowStart);
    }

    private long getCount(String clientId) {
        return jedis.zcard(clientId);
    }
}
