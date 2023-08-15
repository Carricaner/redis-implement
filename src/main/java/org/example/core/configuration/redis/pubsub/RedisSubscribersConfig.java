package org.example.core.configuration.redis.pubsub;

import java.util.HashMap;
import java.util.Map;
import org.example.core.configuration.redis.pubsub.subscribers.MyTopicSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisSubscribersConfig {

  @Bean
  public Map<String, MessageListenerAdapter> redisSubscribers() {
    Map<String, MessageListenerAdapter> map = new HashMap<>();
    map.put("my-topic", new MessageListenerAdapter(new MyTopicSubscriber()));
    // Add other more subscribers if needed
    return map;
  }
}
