package org.example.adapter.ratelimiter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.example.core.domain.ratelimiter.component.TokenBucket;
import org.example.core.domain.ratelimiter.port.BucketRateLimiterAdapter;
import org.example.core.domain.ratelimiter.port.WindowRateLimiterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterAdapterImpl implements WindowRateLimiterAdapter, BucketRateLimiterAdapter {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RateLimiterAdapterImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public long countBetween(String key, long start, long end) {
        return Optional.ofNullable(redisTemplate.opsForZSet().rangeByScore(key, start, end))
                .orElse(Set.of()).size();
    }

    @Override
    public void plusOneVisit(String key, Instant current) {
        redisTemplate.opsForZSet().add(key, current.toString(), current.getEpochSecond());
    }

    @Override
    public void resetAllRecords(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void initializeBucket(String key, long capacity, Instant time) {
        boolean hasKey = redisTemplate.hasKey(key) != null && Boolean.TRUE.equals(redisTemplate.hasKey(key));
        if (!hasKey) {
            Map<String, String> initialInfo = Map.of(
                    getTokensFieldNameOfBucket(), String.valueOf(capacity),
                    getUpdateTimeFieldNameOfBucket(), String.valueOf(time.getEpochSecond())
            );
            redisTemplate.opsForHash().putAll(key, initialInfo);
        }
    }

    @Override
    public Optional<TokenBucket> findTokenBucket(String key) {
        List<Object> result = redisTemplate.opsForHash().multiGet(key, List.of(
                getTokensFieldNameOfBucket(),
                getUpdateTimeFieldNameOfBucket()
        ));
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new TokenBucket(
                        Long.parseLong((String) result.get(0)),
                        Long.parseLong((String) result.get(1))
                ));
    }

    @Override
    public void updateBucketInfo(String key, TokenBucket updatedBucket) {
        redisTemplate.opsForHash().putAll(key, Map.ofEntries(
                Map.entry(getTokensFieldNameOfBucket(), String.valueOf(updatedBucket.tokensNumber())),
                Map.entry(getUpdateTimeFieldNameOfBucket(), String.valueOf(updatedBucket.updateTime()))
        ));
    }
}
