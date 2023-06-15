package org.example.core.configuration.bloomfilter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server.bloom-filter.properties")
@SuppressWarnings("FieldMayBeFinal")
public class BloomFilterPropertiesConfig {
    private int expectedSize = 1000;
    private double falsePositiveRate = 0.001D;

    public int getExpectedSize() {
        return expectedSize;
    }

    public void setExpectedSize(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    public double getFalsePositiveRate() {
        return falsePositiveRate;
    }

    public void setFalsePositiveRate(double falsePositiveRate) {
        this.falsePositiveRate = falsePositiveRate;
    }
}


