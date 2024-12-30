package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class RedisBackedCacheIntTest {

  @Container
  public GenericContainer redis =
      new GenericContainer(DockerImageName.parse("redis:6-alpine")).withExposedPorts(6379);

  private RedissonClient redissonClient;

  @BeforeEach
  public void setUp() {
    Config config = new Config();
    config
        .useSingleServer()
        .setAddress("redis://" + redis.getHost() + ":" + redis.getFirstMappedPort())
        .setConnectionPoolSize(64)
        .setConnectionMinimumIdleSize(10);
    redissonClient = Redisson.create(config);
  }

  @Test
  public void testSimplePutAndGet() {
    RMap<String, String> map = redissonClient.getMap("testMap");

    // Put a value
    map.put("testKey", "testValue");

    // Get the value and assert
    String retrievedValue = map.get("testKey");
    assertThat(retrievedValue).isEqualTo("testValue");
  }
}
