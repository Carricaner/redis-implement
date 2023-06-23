package org.example.core.configuration.redis;

import org.example.core.domain.pubsub.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(basePackages = "org.example.adapter.datasource.redis.repository")
public class RedisConfig {
  private final String redisHost;
  private final int redisPort;

  public RedisConfig(
      @Value("${spring.redis.host:localhost}") String redisHost,
      @Value("${spring.redis.port:6379}") int redisPort) {
    this.redisHost = redisHost;
    this.redisPort = redisPort;
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    // Redis config
    RedisStandaloneConfiguration redisConfig =
        new RedisStandaloneConfiguration(redisHost, redisPort);
    return new JedisConnectionFactory(redisConfig);
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<?, ?> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    return template;
  }

  @Bean
  public MessageListenerAdapter messageListenerAdapter(
      RedisMessageSubscriber redisMessageSubscriber) {
    return new MessageListenerAdapter(redisMessageSubscriber);
  }

  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(
      RedisConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(
        messageListenerAdapter, new ChannelTopic("my-channel"));
    return container;
  }
}
