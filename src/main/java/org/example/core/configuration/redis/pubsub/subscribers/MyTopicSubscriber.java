package org.example.core.configuration.redis.pubsub.subscribers;

import java.nio.charset.StandardCharsets;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MyTopicSubscriber implements MessageListener {

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channel = new String(message.getChannel());
    String messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
    System.out.println("Received message from channel: " + channel);
    System.out.println("Message content: " + messageContent);
  }
}
