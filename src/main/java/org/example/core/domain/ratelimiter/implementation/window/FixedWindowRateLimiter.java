package org.example.core.domain.ratelimiter.implementation.window;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import org.example.core.configuration.ratelimiter.RateLimiterConfigProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class FixedWindowRateLimiter extends WindowRateLimiter {
  private final RedisTemplate<String, String> redisTemplate;

  public FixedWindowRateLimiter(
      RateLimiterConfigProperties properties, RedisTemplate<String, String> redisTemplate) {
    super(properties.getLimitCapacity(), properties.getWindowDuration());
    this.redisTemplate = redisTemplate;
  }

  @Override
  public boolean tryAcquire(String clientId, Instant time) {
    String key = getKeyName(clientId);
    long count = countWithinCurrentMinute(key, time);
    if (count < windowSize) {
      addOneRecord(key, time);
      return true;
    }
    return false;
  }

  @Override
  public void refreshAll(String clientId) {
    redisTemplate.delete(getKeyName(clientId));
  }

  private String getKeyName(String clientId) {
    return WindowRateLimiter.KEY_PREFIX + clientId;
  }

  private long countWithinCurrentMinute(String key, Instant now) {
    return Optional.ofNullable(
            redisTemplate
                .opsForZSet()
                .rangeByScore(
                    key,
                    now.truncatedTo(ChronoUnit.MINUTES).getEpochSecond(),
                    now.plus(1, ChronoUnit.MINUTES)
                        .truncatedTo(ChronoUnit.MINUTES)
                        .getEpochSecond()))
        .orElse(Set.of())
        .size();
  }

  private void addOneRecord(String key, Instant current) {
    redisTemplate.opsForZSet().add(key, current.toString(), current.getEpochSecond());
  }
}
