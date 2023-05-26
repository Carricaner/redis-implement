package org.example.core.configuration;

import org.example.core.utils.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server.rate-limiter.properties")
@SuppressWarnings("FieldMayBeFinal")
public class RateLimiterPropertiesConfig {
    public final static String TYPE_SLIDING_WINDOW = "sliding-window";
    public final static String TYPE_FIXED_WINDOW = "fixed-window";
    public final static String TYPE_TOKEN_BUCKET = "token-bucket";
    public final static String TYPE_LEAKY_BUCKET = "leaky-bucket";
    private final static String TYPE_DEFAULT = TYPE_SLIDING_WINDOW;
    private final static long LIMIT_CAPACITY_DEFAULT = 10L;
    private final static long LIMIT_WINDOW_DURATION = 60L;
    private final static long LIMIT_REFILL_RATE = 10L;
    private final static long LIMIT_LEAKING_RATE = 10L;
    private String type;
    private Long limitCapacity;
    private Long windowDuration;
    private Long refillRate;
    private Long leakingRate;

    public String getType() {
        return StringUtils.isNullOrEmpty(type) ? TYPE_DEFAULT : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLimitCapacity() {
        return limitCapacity == null || limitCapacity <= 0 ? LIMIT_CAPACITY_DEFAULT : limitCapacity;
    }

    public void setLimitCapacity(Long limitCapacity) {
        this.limitCapacity = limitCapacity;
    }

    public Long getWindowDuration() {
        return windowDuration == null || windowDuration <= 0 ? LIMIT_WINDOW_DURATION : windowDuration;
    }

    public void setWindowDuration(Long windowDuration) {
        this.windowDuration = windowDuration;
    }

    public Long getRefillRate() {
        return refillRate == null || refillRate <= 0 ? LIMIT_REFILL_RATE : refillRate;
    }

    public void setRefillRate(Long refillRate) {
        this.refillRate = refillRate;
    }

    public Long getLeakingRate() {
        return leakingRate == null || leakingRate <= 0 ? LIMIT_LEAKING_RATE : leakingRate;
    }

    public void setLeakingRate(Long leakingRate) {
        this.leakingRate = leakingRate;
    }
}


