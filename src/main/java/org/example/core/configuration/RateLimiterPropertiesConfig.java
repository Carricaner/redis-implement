package org.example.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server.rate-limiter.properties")
public class RateLimiterPropertiesConfig {
    private String type;
    private String limitCapacity;
    private String windowDuration;
    private String refillRate;
    private String leakingRate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLimitCapacity() {
        return limitCapacity;
    }

    public void setLimitCapacity(String limitCapacity) {
        this.limitCapacity = limitCapacity;
    }

    public String getWindowDuration() {
        return windowDuration;
    }

    public void setWindowDuration(String windowDuration) {
        this.windowDuration = windowDuration;
    }

    public String getRefillRate() {
        return refillRate;
    }

    public void setRefillRate(String refillRate) {
        this.refillRate = refillRate;
    }

    public String getLeakingRate() {
        return leakingRate;
    }

    public void setLeakingRate(String leakingRate) {
        this.leakingRate = leakingRate;
    }
}
