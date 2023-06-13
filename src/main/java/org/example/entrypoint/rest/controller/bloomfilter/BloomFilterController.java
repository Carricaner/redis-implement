package org.example.entrypoint.rest.controller.bloomfilter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.usecase.bloomfilter.BloomFilterUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bloom-filter")
public class BloomFilterController {
  private final BloomFilterUseCase bloomFilterUseCase;

  public BloomFilterController(BloomFilterUseCase bloomFilterUseCase) {
    this.bloomFilterUseCase = bloomFilterUseCase;
  }

  @PostMapping("/{clientId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addClient(@PathVariable("clientId") String clientId, HttpServletRequest request) {
    bloomFilterUseCase.add(clientId);
  }

  @GetMapping("/{clientId}")
  public boolean clientExists(
      @PathVariable("clientId") String clientId, HttpServletRequest request) {
    return bloomFilterUseCase.contains(clientId);
  }
}
