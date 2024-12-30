package org.example.core.domain.bloomfilter.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.container.RedisBaseTest;
import org.example.core.configuration.bloomfilter.BloomFilterConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class RedissonBloomFilterTest extends RedisBaseTest {
  private RedissonClient redissonClient;

  @BeforeEach
  public void setUpContainer() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://" + getRedisHost() + ":" + getRedisPort());
    redissonClient = Redisson.create(config);
  }

  @Test
  void test_Add() {
    BloomFilterConfigProperties properties = new BloomFilterConfigProperties();
    properties.setExpectedSize(1000L);
    properties.setFalsePositiveRate(0.001D);
    RedissonBloomFilter redissonBloomFilter = new RedissonBloomFilter(properties, redissonClient);
    redissonBloomFilter.add("key", "twen");
    assertThat(redissonBloomFilter.contains("key", "twen")).isTrue();
    assertThat(redissonBloomFilter.contains("key", "water-tiger-twen")).isFalse();
  }
}
