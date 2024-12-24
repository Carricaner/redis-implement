package org.example.core.configuration.ratelimiter;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterConfigProperties {
  public static final String SLIDING_WINDOW_TYPE = "sliding-window";
  public static final String FIXED_WINDOW_TYPE = "fixed-window";
  public static final String TOKEN_BUCKET_TYPE = "token-bucket";
  public static final String LEAKY_BUCKET_TYPE = "leaky-bucket";

  private RateLimiterType type = RateLimiterType.SLIDING_WINDOW;
  private Long limitCapacity = 10L;
  private Long windowDuration = 60L;
  private Long refillRate = 10L;
  private Long leakingRate = 10L;

  @Getter
  public enum RateLimiterType {
    SLIDING_WINDOW("sliding-window"),
    FIXED_WINDOW("fixed-window"),
    TOKEN_BUCKET("token-bucket"),
    LEAKY_BUCKET("leaky-bucket");

    private final String name;

    RateLimiterType(String name) {
      this.name = name;
    }
  }
}
