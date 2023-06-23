package org.example.core.domain.pubsub;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher {
  private final RedisTemplate<String, String> redisTemplate;

  public RedisMessagePublisher(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void publishMessage(String channel, String message) {
    ChannelTopic channelTopic = new ChannelTopic(channel);
    redisTemplate.convertAndSend(channelTopic.getTopic(), message);
  }
}
