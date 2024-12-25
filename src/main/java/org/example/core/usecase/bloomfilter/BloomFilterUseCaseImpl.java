package org.example.core.usecase.bloomfilter;

import org.example.core.domain.bloomfilter.BloomFilterManager;
import org.springframework.stereotype.Service;

@Service
public class BloomFilterUseCaseImpl implements BloomFilterUseCase {
  private final BloomFilterManager bloomFilterManager;

  public BloomFilterUseCaseImpl(BloomFilterManager bloomFilterManager) {
    this.bloomFilterManager = bloomFilterManager;
  }

  @Override
  public void add(String element) {
    bloomFilterManager.add(element);
  }

  @Override
  public boolean contains(String element) {
    return bloomFilterManager.contains(element);
  }
}
