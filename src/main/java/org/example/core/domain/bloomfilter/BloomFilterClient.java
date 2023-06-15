package org.example.core.domain.bloomfilter;

import org.example.core.configuration.bloomfilter.BloomFilterPropertiesConfig;
import org.example.core.domain.bloomfilter.port.BloomFilterAdapter;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterClient {
  private final BloomFilterAdapter bloomFilterAdapter;
  private final int expectedSize;
  private final double falsePositiveRate;

  public BloomFilterClient(
      BloomFilterPropertiesConfig bloomFilterPropertiesConfig,
      BloomFilterAdapter bloomFilterAdapter) {
    expectedSize = bloomFilterPropertiesConfig.getExpectedSize();
    falsePositiveRate = bloomFilterPropertiesConfig.getFalsePositiveRate();
    this.bloomFilterAdapter = bloomFilterAdapter;
  }

  public BloomFilter<String> getBloomFilter() {
    return new DefaultBloomFilter<>(expectedSize, falsePositiveRate, bloomFilterAdapter);
  }
}
