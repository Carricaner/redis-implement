package org.example.core.domain.bloomfilter.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.example.container.RedisContainerBaseTest;
import org.example.core.configuration.bloomfilter.BloomFilterConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class RedissonBloomFilterTests extends RedisContainerBaseTest {
  private RedissonClient redissonClient;

  @BeforeEach
  public void setUpContainer() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://" + getRedisHost() + ":" + getRedisPort());
    redissonClient = Redisson.create(config);
  }

  private static final String BLOOM_FILTER_KEY = "key";

  private static Stream<Arguments> provide_Happy_Case_Arguments() {
    return Stream.of(
        Arguments.of(BLOOM_FILTER_KEY, "cat", true),
        Arguments.of(BLOOM_FILTER_KEY, "dog", false)
    );
  }

  @ParameterizedTest
  @MethodSource("provide_Happy_Case_Arguments")
  void happy_passes(String bloomFilterKey, String value, boolean existed) {
    BloomFilterConfigProperties properties = new BloomFilterConfigProperties();
    properties.setExpectedSize(1000L);
    properties.setFalsePositiveRate(0.001D);
    RedissonBloomFilter redissonBloomFilter = new RedissonBloomFilter(properties, redissonClient);
    redissonBloomFilter.add(BLOOM_FILTER_KEY, "cat");
    assertThat(redissonBloomFilter.contains(bloomFilterKey, value)).isEqualTo(existed);
  }
}
