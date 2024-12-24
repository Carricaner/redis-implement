package org.example.entry.rest.controller.distributedlock;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.usecase.distributedlock.DistributedLockUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/distributed-lock")
public class DistributedLockController {

  private final DistributedLockUseCase distributedLockUseCase;

  public DistributedLockController(DistributedLockUseCase distributedLockUseCase) {
    this.distributedLockUseCase = distributedLockUseCase;
  }

  @GetMapping("/my-lock")
  public String clientExists(HttpServletRequest request) throws InterruptedException {
    return distributedLockUseCase.accessReadLockAndGetTheResource();
  }
}
