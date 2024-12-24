package org.example.core.configuration.ratelimiter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterPropertiesConfig {
  public static final String TYPE_SLIDING_WINDOW = "sliding-window";
  public static final String TYPE_FIXED_WINDOW = "fixed-window";
  public static final String TYPE_TOKEN_BUCKET = "token-bucket";
  public static final String TYPE_LEAKY_BUCKET = "leaky-bucket";

  private String type = TYPE_SLIDING_WINDOW;
  private Long limitCapacity = 10L;
  private Long windowDuration = 60L;
  private Long refillRate = 10L;
  private Long leakingRate = 10L;
}
