package org.example.core.configuration.web;

import jakarta.annotation.PreDestroy;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class ShutdownConfig {
  private final RedissonClient redissonClient;
  private final JedisConnectionFactory jedisConnectionFactory;

  public ShutdownConfig(
      RedissonClient redissonClient, JedisConnectionFactory jedisConnectionFactory) {
    this.redissonClient = redissonClient;
    this.jedisConnectionFactory = jedisConnectionFactory;
  }

  @PreDestroy
  public void shutdown() {
    if (redissonClient != null) {
      redissonClient.shutdown();
    }

    if (jedisConnectionFactory != null) {
      jedisConnectionFactory.destroy();
    }
  }
}
