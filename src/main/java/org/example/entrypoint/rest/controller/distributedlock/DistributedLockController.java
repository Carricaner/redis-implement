package org.example.entrypoint.rest.controller.distributedlock;

import jakarta.servlet.http.HttpServletRequest;
import org.example.core.usecase.distributedlock.DistributedLockCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/distributed-lock")
public class DistributedLockController {

  private final DistributedLockCase distributedLockCase;

  public DistributedLockController(DistributedLockCase distributedLockCase) {
    this.distributedLockCase = distributedLockCase;
  }

  @GetMapping("/my-lock")
  public String clientExists(HttpServletRequest request) throws InterruptedException {
    return distributedLockCase.accessReadLockAndGetTheResource();
  }
}
