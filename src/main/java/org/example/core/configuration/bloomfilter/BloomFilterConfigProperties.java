package org.example.core.configuration.bloomfilter;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bloom-filter")
public class BloomFilterConfigProperties {
  private long expectedSize = 1000L;
  private double falsePositiveRate = 0.001D;
}
