package org.example.core.domain.ratelimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.Instant;

@Component
@Qualifier("sliding-window")
public class SlidingWindowRateLimiter implements RateLimiter {
    private static final String KEY_PREFIX = "rate:";
    private final int limit;
    private final int windowDuration;
    private final Jedis jedis = new Jedis("localhost", 6380); // temp

    @Autowired
    public SlidingWindowRateLimiter(
            @Value("${server.rateLimiter.properties.limitCapacity:10}") int limit,
            @Value("${server.rateLimiter.properties.windowDuration:60}") int windowDuration) {
        this.limit = limit;
        this.windowDuration = windowDuration;
    }

    private String getKey(String clientId) {
        return KEY_PREFIX + clientId;
    }

    @Override
    public boolean isAllowed(String clientId) {
        boolean result = false;
        long currentTime = Instant.now().getEpochSecond();
        if (!exceedLimit(getKey(clientId), currentTime)) {
            updateRecord(clientId, currentTime);
            result = true;
        }
        return result;
    }

    private boolean exceedLimit(String clientId, long currentTime) {
        long number = jedis.zcount(getKey(clientId), (double) currentTime - windowDuration, (double) currentTime);
        return number >= limit;
    }

    private void updateRecord(String clientId, long currentTime) {
        jedis.zadd(getKey(clientId), currentTime, String.valueOf(currentTime));
    }
}
