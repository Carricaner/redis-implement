package org.example.core.domain.bloomfilter.implementation;

import org.example.core.configuration.bloomfilter.BloomFilterConfigProperties;
import org.example.core.domain.bloomfilter.BloomFilter;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class RedissonBloomFilter implements BloomFilter<Object> {
  private final RedissonClient redissonClient;
  private final BloomFilterConfigProperties properties;

  public RedissonBloomFilter(
      BloomFilterConfigProperties properties, RedissonClient redissonClient) {
    this.properties = properties;
    this.redissonClient = redissonClient;
  }

  private RBloomFilter<Object> getBloomFilter(String clientId) {
    RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(clientId);
    bloomFilter.tryInit(properties.getExpectedSize(), properties.getFalsePositiveRate());
    return bloomFilter;
  }

  @Override
  public void add(String key, Object element) {
    getBloomFilter(key).add(element);
  }

  @Override
  public boolean contains(String key, Object element) {
    return getBloomFilter(key).contains(element);
  }
}
