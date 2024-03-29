package org.example.core.configuration.redis;

import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

  @Bean
  public RedissonClient redissonClient() throws IOException {
    //    File redissonConfigFile = new ClassPathResource("redisson-config.yaml").getFile();
    //    Config config = Config.fromYAML(redissonConfigFile);
    //    return Redisson.create(config);
    Config config = new Config();
    config
        .useClusterServers()
        .setScanInterval(2000)
        .addNodeAddress(
            "redis://node-1:7000",
            "redis://node-2:7001",
            "redis://node-3:7002",
            "redis://node-4:7003",
            "redis://node-5:7004",
            "redis://node-6:7005");
    return Redisson.create(config);
  }
}
