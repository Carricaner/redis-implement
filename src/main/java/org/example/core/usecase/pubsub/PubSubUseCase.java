package org.example.core.usecase.pubsub;

import org.example.core.configuration.redis.pubsub.publishers.RedisMessagePublisher;
import org.springframework.stereotype.Service;

@Service
public class PubSubUseCase {
  private final RedisMessagePublisher redisMessagePublisher;

  public PubSubUseCase(RedisMessagePublisher redisMessagePublisher) {
    this.redisMessagePublisher = redisMessagePublisher;
  }

  public void trySend(String message) {
    String channelTopic = "my-channel";
    redisMessagePublisher.publishMessage(channelTopic, message);
  }
}
