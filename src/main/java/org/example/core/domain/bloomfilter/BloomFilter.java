package org.example.core.domain.bloomfilter;

public interface BloomFilter<T> {
  void add(String key, T element);
  boolean contains(String key, T element);
}
