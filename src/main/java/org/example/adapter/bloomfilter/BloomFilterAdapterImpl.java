package org.example.adapter.bloomfilter;

import org.example.core.domain.bloomfilter.port.BloomFilterAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterAdapterImpl implements BloomFilterAdapter {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX_KEY = "BloomFilter_";

    public BloomFilterAdapterImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void add(String filterId, Integer offset) {
        redisTemplate.opsForValue().setBit(getKey(filterId), offset, true);
    }

    @Override
    public boolean contains(String filterId, Integer offset) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(getKey(filterId), offset));
    }

    private String getKey(String bloomFilterIdInString) {
        return PREFIX_KEY + bloomFilterIdInString;
    }
}
