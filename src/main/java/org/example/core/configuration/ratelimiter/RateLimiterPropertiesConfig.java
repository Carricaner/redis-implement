package org.example.core.configuration.ratelimiter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server.rate-limiter.properties")
@SuppressWarnings("FieldMayBeFinal")
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getLimitCapacity() {
    return limitCapacity;
  }

  public void setLimitCapacity(Long limitCapacity) {
    this.limitCapacity = limitCapacity;
  }

  public Long getWindowDuration() {
    return windowDuration;
  }

  public void setWindowDuration(Long windowDuration) {
    this.windowDuration = windowDuration;
  }

  public Long getRefillRate() {
    return refillRate;
  }

  public void setRefillRate(Long refillRate) {
    this.refillRate = refillRate;
  }

  public Long getLeakingRate() {
    return leakingRate;
  }

  public void setLeakingRate(Long leakingRate) {
    this.leakingRate = leakingRate;
  }
}
