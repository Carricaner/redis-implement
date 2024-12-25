package org.example.entry.rest.controller.distributedlock;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.domain.ratelimiter.RateLimited;
import org.example.core.usecase.distributedlock.DistributedLockUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RateLimited
@RestController
@RequestMapping("/distributed-lock")
public class DistributedLockController {
  private final DistributedLockUseCase distributedLockUseCase;

  public DistributedLockController(DistributedLockUseCase distributedLockUseCase) {
    this.distributedLockUseCase = distributedLockUseCase;
  }

  @GetMapping("/read-write-lock/my-lock")
  public String read(HttpServletRequest request) throws InterruptedException {
    return distributedLockUseCase.acquireReadLock();
  }

  @PostMapping("/read-write-lock/my-lock")
  public String write(HttpServletRequest request) throws InterruptedException {
    return distributedLockUseCase.acquireWriteLock();
  }
}
