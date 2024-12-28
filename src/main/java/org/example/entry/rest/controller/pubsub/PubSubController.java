package org.example.entry.rest.controller.pubsub;

import org.example.core.usecase.pubsub.PubSubUseCase;
import org.example.core.usecase.pubsub.output.PublishMessageUsecaseOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pub-sub")
public class PubSubController {
  private final PubSubUseCase pubSubUseCase;

  public PubSubController(PubSubUseCase pubSubUseCase) {
    this.pubSubUseCase = pubSubUseCase;
  }

  @PostMapping("/publish")
  public PublishMessageUsecaseOutput publishMessage(
      @RequestBody PublishMessageRequestBody requestBody) {
    return pubSubUseCase.publishMessage(requestBody.topic(), requestBody.message());
  }
}
