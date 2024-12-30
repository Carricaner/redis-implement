package org.example.container;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class RedisBaseTest {

  @Container
  protected static final GenericContainer<?> redisContainer =
      new GenericContainer<>("redis:6-alpine")
          .withExposedPorts(6379)
          .withReuse(true); // Enable reuse

  @BeforeAll
  static void startContainer() {
    redisContainer.start();
  }

  protected String getRedisHost() {
    return redisContainer.getHost();
  }

  protected Integer getRedisPort() {
    return redisContainer.getFirstMappedPort();
  }
}
