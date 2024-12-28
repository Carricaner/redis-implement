package org.example.core.configuration.redis.pubsub;

import jakarta.annotation.PostConstruct;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisPubSubConfig {
  private final RedissonClient redissonClient;

  public RedisPubSubConfig(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @PostConstruct
  public void registerListeners() {
    redissonClient
        .getTopic("main")
        .addListener(
            Void.class,
            ((channel, msg) -> {
              System.out.println(msg);
            }));
  }
}
