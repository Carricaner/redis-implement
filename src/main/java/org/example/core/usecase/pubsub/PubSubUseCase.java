package org.example.core.usecase.pubsub;

import org.example.core.domain.pubsub.RedisMessagePublisher;
import org.example.core.domain.pubsub.RedisMessageSubscriber;
import org.springframework.stereotype.Service;

@Service
public class PubSubUseCase {
  private final RedisMessagePublisher redisMessagePublisher;

  public PubSubUseCase(RedisMessagePublisher redisMessagePublisher, RedisMessageSubscriber redisMessageSubscriber) {
    this.redisMessagePublisher = redisMessagePublisher;
  }

  public void trySend(String message) {
    String channelTopic = "my-channel";
    redisMessagePublisher.publishMessage(channelTopic, message);
  }
}
