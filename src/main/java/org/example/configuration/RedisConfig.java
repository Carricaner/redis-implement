package org.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {
    private final String redisHost;
    private final int redisPort;

    public RedisConfig(
            @Value("${spring.redis.host}") String redisHost,
            @Value("${spring.redis.port}") int redisPort) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }

    @Bean
    public Jedis redisClient() {
        return new Jedis(redisHost,  redisPort);
    }
}
