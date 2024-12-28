package org.example.core.usecase.pubsub;

import org.example.core.usecase.pubsub.output.PublishMessageUsecaseOutput;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
public class PubSubUseCase {
  private final RedissonClient redissonClient;

  public PubSubUseCase(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  public PublishMessageUsecaseOutput publishMessage(String topic, String message) {
    return new PublishMessageUsecaseOutput(true, redissonClient.getTopic(topic).publish(message));
  }
}
