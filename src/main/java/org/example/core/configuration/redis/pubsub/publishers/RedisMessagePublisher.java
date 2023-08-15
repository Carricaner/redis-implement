package org.example.core.configuration.redis.pubsub.publishers;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher {
  private final RedisTemplate<String, String> redisTemplate;

  public RedisMessagePublisher(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void publishMessage(String topic, String message) {
    redisTemplate.convertAndSend(new ChannelTopic(topic).getTopic(), message);
  }
}
