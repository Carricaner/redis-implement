package org.example.core.domain.bloomfilter;

import java.util.UUID;
import java.util.function.Function;
import org.example.core.domain.bloomfilter.port.BloomFilterAdapter;

public class DefaultBloomFilter<T> implements BloomFilter<T> {
  private final UUID ID = UUID.randomUUID();
  private final BloomFilterAdapter bloomFilterAdapter;
  private final int size;
  private final Function<T, Integer>[] hashFunctions;

  public DefaultBloomFilter(
      int expectedSize, double falsePositiveRate, BloomFilterAdapter bloomFilterAdapter) {
    this.bloomFilterAdapter = bloomFilterAdapter;
    this.size = getOptimalSize(expectedSize, falsePositiveRate);
    this.hashFunctions = createHashFunctions(optimalNumHashFunctions(expectedSize, size));
  }

  private String getKey() {
    return ID.toString();
  }

  @Override
  public void add(T element) {
    for (Function<T, Integer> hashFunction : hashFunctions) {
      int hash = hashFunction.apply(element);
      bloomFilterAdapter.add(getKey(), hash % size);
    }
  }

  @Override
  public boolean contains(T element) {
    for (Function<T, Integer> hashFunction : hashFunctions) {
      int hash = hashFunction.apply(element);
      if (!bloomFilterAdapter.contains(getKey(), hash % size)) {
        return false;
      }
    }
    return true;
  }

  private int optimalNumHashFunctions(int expectedSize, int size) {
    return (int) Math.ceil(((double) size / expectedSize) * Math.log(2.0));
  }

  private Function<T, Integer>[] createHashFunctions(int numHashFunctions) {
    @SuppressWarnings("unchecked")
    Function<T, Integer>[] hashFunctions = new Function[numHashFunctions];
    for (int i = 0; i < numHashFunctions; i++) {
      int seed = i;
      hashFunctions[i] = element -> (Math.abs(element.hashCode() ^ seed));
    }
    return hashFunctions;
  }
}
