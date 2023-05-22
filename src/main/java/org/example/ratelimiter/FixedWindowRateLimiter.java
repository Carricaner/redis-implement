package org.example.ratelimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FixedWindowRateLimiter implements RateLimiter{
    private static final String KEY_NAME = "rate";
    private final int limit;
    private final int windowDuration;
    private final Jedis jedis;

    @Autowired
    public FixedWindowRateLimiter(
            @Value("${server.rateLimiter.properties.limitCapacity:10}") int limit,
            @Value("${server.rateLimiter.properties.windowDuration:60}") int windowDuration,
            Jedis jedis) {
        this.limit = limit;
        this.windowDuration = windowDuration;
        this.jedis = jedis;
    }

    @Override
    public boolean isAllowed(String clientId) {
        boolean result = false;
        int count = getCount(clientId);
        if (count < limit) {
            jedis.hincrBy(KEY_NAME, clientId, 1);
            long timeLeft = getLeftTime();
            jedis.expire(KEY_NAME, timeLeft);
            result = true;
        }
        return result;
    }

    private int getCount(String clientId) {
        return jedis.hget(KEY_NAME, clientId) == null ?
                0 : Integer.parseInt(jedis.hget(KEY_NAME, clientId));
    }

    private long getLeftTime() {
    	return windowDuration - Instant.now().getEpochSecond()
                + Instant.now().truncatedTo(ChronoUnit.MINUTES).getEpochSecond();
    }
}

