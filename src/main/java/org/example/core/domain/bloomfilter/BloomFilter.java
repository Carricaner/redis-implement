package org.example.core.domain.bloomfilter;

public interface BloomFilter<T> {
  void add(T element);

  boolean contains(T element);

  default int getOptimalSize(int expectedSize, double falsePositiveRate) {
    return (int)
        Math.ceil(
            (expectedSize * Math.log(falsePositiveRate))
                / Math.log(1.0 / (Math.pow(2.0, Math.log(2.0)))));
  }
}
