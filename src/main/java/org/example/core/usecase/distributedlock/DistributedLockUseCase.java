package org.example.core.usecase.distributedlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
public class DistributedLockUseCase {
  private final RedissonClient redissonClient;

  private final AtomicInteger counter = new AtomicInteger(0);

  public DistributedLockUseCase(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  // Read Lock -> blocking until former release
  public String acquireReadLock() {
    RLock lock = redissonClient.getReadWriteLock("myLock").readLock();
    try {
      boolean unLocked = lock.tryLock(500, 3500, TimeUnit.MILLISECONDS);
      return unLocked ? String.valueOf(counter.get()) : "None";
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  // Wrote Lock -> acquire the lock or wait -> exceeds waitTime --> return false
  public String acquireWriteLock() {
    RLock lock = redissonClient.getReadWriteLock("myLock").writeLock();
    boolean unLocked = false;
    try {
      unLocked = lock.tryLock(500, 1800, TimeUnit.MILLISECONDS);
      if (unLocked) {
        mockWrite();
      }
      return unLocked ? "Writing completed" : "Could not acquire lock. Please try again later.";
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return "Thread was interrupted.";
    } finally {
      if (unLocked) {
        lock.unlock();
      }
    }
  }

  private void mockWrite() throws InterruptedException {
    Thread.sleep(1500);
    counter.addAndGet(1);
  }
}
