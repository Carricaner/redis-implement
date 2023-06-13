package org.example.core.domain.bloomfilter.port;

public interface BloomFilterAdapter {
    void add(String key, Integer value);
    boolean contains(String key, Integer value);
}
