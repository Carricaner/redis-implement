package org.example.core.usecase.bloomfilter;

import org.example.core.domain.bloomfilter.BloomFilterClient;
import org.springframework.stereotype.Service;

@Service
public class BloomFilterUseCaseImpl implements BloomFilterUseCase {
    private final BloomFilterClient bloomFilterClient;

    public BloomFilterUseCaseImpl(BloomFilterClient bloomFilterClient) {
        this.bloomFilterClient = bloomFilterClient;
    }

    @Override
    public void add(String element) {
        bloomFilterClient.getBloomFilter().add(element);
    }

    @Override
    public boolean contains(String element) {
        return bloomFilterClient.getBloomFilter().contains(element);
    }
}
