package org.example.core.domain.ratelimiter.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.example.container.RedisContainerBaseTest;
import org.example.core.configuration.ratelimiter.RateLimiterConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class RedissonRateLimiterTest extends RedisContainerBaseTest {
  private RedissonClient redissonClient;

  @BeforeEach
  public void setUpContainer() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://" + getRedisHost() + ":" + getRedisPort());
    redissonClient = Redisson.create(config);
  }

  @Test
  void happy_pass() {
    RateLimiterConfigProperties properties = new RateLimiterConfigProperties();
    properties.setLimitCapacity(1L);
    properties.setWindowDuration(1L);
    RedissonRateLimiter rateLimiter = new RedissonRateLimiter(properties, redissonClient);
    String clientId = "clientId";
    Instant instant = Instant.now();
    rateLimiter.refreshAll(clientId);
    assertThat(rateLimiter.tryAcquire(clientId, instant)).isEqualTo(true);
    assertThat(rateLimiter.tryAcquire(clientId, instant.plusMillis(300))).isEqualTo(false);
  }
}
