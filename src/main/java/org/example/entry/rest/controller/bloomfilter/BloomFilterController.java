package org.example.entry.rest.controller.bloomfilter;

import org.example.core.domain.ratelimiter.RateLimited;
import org.example.core.usecase.bloomfilter.BloomFilterUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RateLimited
@RestController
@RequestMapping("/bloom-filter")
public class BloomFilterController {
  private final BloomFilterUseCase bloomFilterUseCase;

  public BloomFilterController(BloomFilterUseCase bloomFilterUseCase) {
    this.bloomFilterUseCase = bloomFilterUseCase;
  }

  @PostMapping("/{clientId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addClient(@PathVariable("clientId") String clientId) {
    bloomFilterUseCase.add(clientId);
  }

  @GetMapping("/{clientId}")
  public boolean clientExists(@PathVariable("clientId") String clientId) {
    return bloomFilterUseCase.contains(clientId);
  }
}
