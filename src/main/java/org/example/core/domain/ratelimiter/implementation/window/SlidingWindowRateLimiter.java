package org.example.core.domain.ratelimiter.implementation.window;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.example.core.configuration.ratelimiter.RateLimiterConfigProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowRateLimiter extends WindowRateLimiter {
  private final RedisTemplate<String, String> redisTemplate;

  public SlidingWindowRateLimiter(
      RateLimiterConfigProperties properties, RedisTemplate<String, String> redisTemplate) {
    super(properties.getLimitCapacity(), properties.getWindowDuration());
    this.redisTemplate = redisTemplate;
  }

  @Override
  public boolean isAllowed(String clientId, Instant time) {
    String key = getKeyName(clientId);
    if (!exceedLimit(key, time)) {
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
    return KEY_PREFIX + clientId;
  }

  private boolean exceedLimit(String key, Instant current) {
    return Optional.ofNullable(
                redisTemplate
                    .opsForZSet()
                    .rangeByScore(
                        key, current.getEpochSecond() - windowDuration, current.getEpochSecond()))
            .orElse(Set.of())
            .size()
        >= windowSize;
  }

  private void addOneRecord(String key, Instant currentTime) {
    redisTemplate.opsForZSet().add(key, currentTime.toString(), currentTime.getEpochSecond());
  }
}
