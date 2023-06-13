package org.example.core.usecase.bloomfilter;

public interface BloomFilterUseCase {
    void add(String str);
    boolean contains(String str);
}
