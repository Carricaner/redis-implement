package org.example.core.domain.bloomfilter;

import org.example.core.domain.bloomfilter.port.BloomFilterAdapter;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterClient {
  private final BloomFilterAdapter bloomFilterAdapter;
  private final static int DEFAULT_EXPECTED_SIZE = 1000;
  private final static double DEFAULT_FALSE_POSITIVE_RATE = 0.01D;

  public BloomFilterClient(BloomFilterAdapter bloomFilterAdapter) {
    this.bloomFilterAdapter = bloomFilterAdapter;
  }

  public BloomFilter<String> getBloomFilter() {
    return new DefaultBloomFilter<>(DEFAULT_EXPECTED_SIZE, DEFAULT_FALSE_POSITIVE_RATE, bloomFilterAdapter);
  }
}
