package org.example.core.configuration.redis.redisson;

import org.example.core.configuration.redis.RedisConfigProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

  @Bean
  public RedissonClient redissonClient(RedisConfigProperties properties) {
    Config config = new Config();
    config
        .useSingleServer()
        .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
        .setUsername(properties.getUsername())
        .setPassword(properties.getPassword())
        .setConnectionPoolSize(64)
        .setConnectionMinimumIdleSize(10);
    return Redisson.create(config);
  }
}
