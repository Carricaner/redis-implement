package org.example.core.configuration.bloomfilter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bloom-filter")
public class BloomFilterPropertiesConfig {
    private int expectedSize = 1000;
    private double falsePositiveRate = 0.001D;
}


