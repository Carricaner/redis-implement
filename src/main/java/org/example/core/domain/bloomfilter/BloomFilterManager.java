package org.example.core.domain.bloomfilter;

import org.springframework.stereotype.Component;

@Component
public class BloomFilterManager {
  private final String bloomFilterKey = "bloom-filter";
  private final BloomFilter<Object> bloomFilter;

  public BloomFilterManager(BloomFilter<Object> bloomFilter) {
    this.bloomFilter = bloomFilter;
  }

  public void add(Object element) {
    bloomFilter.add(bloomFilterKey, element);
  }

  public boolean contains(Object element) {
    return bloomFilter.contains(bloomFilterKey, element);
  }
}
