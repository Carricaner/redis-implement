package org.example.entrypoint.rest.controller.pubsub;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.usecase.pubsub.PubSubUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pub-sub")
public class PubSubController {
  private final PubSubUseCase pubSubUseCase;

  public PubSubController(PubSubUseCase pubSubUseCase) {
    this.pubSubUseCase = pubSubUseCase;
  }

  @PostMapping("/{message}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void clientExists(@PathVariable("message") String message, HttpServletRequest request) {
    pubSubUseCase.trySend(message);
  }
}
