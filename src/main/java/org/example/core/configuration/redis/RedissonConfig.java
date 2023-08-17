package org.example.core.configuration.redis;

import java.io.File;
import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class RedissonConfig {

  @Bean
  public RedissonClient redissonClient() throws IOException {
    File redissonConfigFile = new ClassPathResource("redisson-config.yaml").getFile();
    Config config = Config.fromYAML(redissonConfigFile);
    return Redisson.create(config);
  }
}
