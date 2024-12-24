package org.example.core.configuration.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfigProperties {
  private String host;
  private int port = 16463;
  private String username;
  private String password;
}
