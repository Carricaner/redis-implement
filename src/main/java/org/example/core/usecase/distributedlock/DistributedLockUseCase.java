package org.example.core.usecase.distributedlock;

import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
public class DistributedLockUseCase {

  private final RedissonClient redissonClient;

  public DistributedLockUseCase(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  public String accessReadLockAndGetTheResource() throws InterruptedException {
    String lockName = "myLock";
    RReadWriteLock rwlock = redissonClient.getReadWriteLock(lockName);
    RLock lock = rwlock.readLock();

    // The first argument is the waiting time(s) for someone to get the lock.
    // The second argument is the waiting time(s) for the lock when it is not unlocked by the logic
    // below.
    // When the period is longer then the second argument, the lock will release automatically.
    boolean res = lock.tryLock(100, 3, TimeUnit.SECONDS);

    if (res) {
      try {
        Thread.sleep(5000);
        System.out.println("Everything's good!");
      } finally {
        lock.unlock();
      }
    }
    return "the-resource";
  }
}
