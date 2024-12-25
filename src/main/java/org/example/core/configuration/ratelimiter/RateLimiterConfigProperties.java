package org.example.core.configuration.ratelimiter;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterConfigProperties {
  private RateLimiterType type;
  private Long limitCapacity = 10L;
  private Long windowDuration = 60L;
  private Long refillRate = 10L;
  private Long leakingRate = 10L;

  @Getter
  public enum RateLimiterType {
    SLIDING_WINDOW("sliding-window"),
    FIXED_WINDOW("fixed-window"),
    TOKEN_BUCKET("token-bucket"),
    LEAKY_BUCKET("leaky-bucket"),
    REDISSON("redisson");

    private final String name;

    RateLimiterType(String name) {
      this.name = name;
    }
  }
}
