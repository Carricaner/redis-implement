package org.example.adapter.ratelimiter;

import org.example.core.domain.ratelimiter.port.FixedBucketRateLimiterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RateLimiterAdapterImpl implements FixedBucketRateLimiterAdapter {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RateLimiterAdapterImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public long countBetween(String key, long start, long end) {
        return redisTemplate.opsForZSet().count(key, start, end);
    }

    @Override
    public void plusOneVisit(String key, Instant current) {
        redisTemplate.opsForZSet().add(key, current.toString(), current.getEpochSecond());
    }

    @Override
    public void resetAllRecords(String key) {
        redisTemplate.delete(key);
    }
}
