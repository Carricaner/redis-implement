package org.example.core.domain.ratelimiter.implementation.bucket;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.example.core.configuration.ratelimiter.RateLimiterConfigProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class LeakyBucketRateLimiter extends BucketRateLimiter {
  private final RedisTemplate<String, String> redisTemplate;

  public LeakyBucketRateLimiter(
      RateLimiterConfigProperties properties, RedisTemplate<String, String> redisTemplate) {
    super(properties.getLimitCapacity(), properties.getLeakingRate());
    this.redisTemplate = redisTemplate;
  }

  private String getKey(String clientId) {
    return KEY_PREFIX + clientId;
  }

  void createIfAbsent(String key) {
    boolean hasKey =
        redisTemplate.hasKey(key) != null && Boolean.TRUE.equals(redisTemplate.hasKey(key));
    if (!hasKey) {
      Map<String, String> initialInfo =
          Map.of(
              "token", String.valueOf(capacity),
              "updateAt", String.valueOf(Instant.now().getEpochSecond()));
      redisTemplate.opsForHash().putAll(key, initialInfo);
    }
  }

  @Override
  public boolean tryAcquire(String clientId, Instant time) {
    String key = getKey(clientId);
    createIfAbsent(key);

    List<Object> result = redisTemplate.opsForHash().multiGet(key, List.of("token", "updateAt"));
    if (result.isEmpty()) {
      return false;
    }
    Optional<TokenBucket> op =
        Optional.of(
            new TokenBucket(
                Long.parseLong((String) result.get(0)), Long.parseLong((String) result.get(1))));

    TokenBucket bucket = op.get();
    if (calculateTokens(bucket, time) <= capacity) {
      updateTokenInfo(key, bucket, time);
      return true;
    }
    return false;
  }

  @Override
  public void refreshAll(String clientId) {
    redisTemplate.delete(getKey(clientId));
  }

  private long calculateTokens(TokenBucket bucket, Instant currentTime) {
    long tokensToBeLeaked =
        needLeaking(currentTime.getEpochSecond(), bucket.updateTime()) ? rate : 0;
    return Math.max(0, bucket.tokensNumber() - tokensToBeLeaked) + TOKEN_NUMBER_TO_BE_ADDED;
  }

  private void updateTokenInfo(String key, TokenBucket bucket, Instant currentTime) {
    long newTokenNumber = calculateTokens(bucket, currentTime);
    long newUpdateTime =
        needLeaking(currentTime.getEpochSecond(), bucket.updateTime())
            ? currentTime.getEpochSecond()
            : bucket.updateTime();
    TokenBucket updatedBucket = new TokenBucket(newTokenNumber, newUpdateTime);
    redisTemplate
        .opsForHash()
        .putAll(
            key,
            Map.ofEntries(
                Map.entry("token", String.valueOf(updatedBucket.tokensNumber())),
                Map.entry("updateAt", String.valueOf(updatedBucket.updateTime()))));
  }

  private boolean needLeaking(long currentTime, long lastLeakingTime) {
    return (currentTime - lastLeakingTime) / 60 > 0;
  }
}
