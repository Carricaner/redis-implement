package org.example.ratelimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.time.Instant;
import java.util.Map;

@Component
@Qualifier("leaking-bucket-rate-limiter")
public class LeakyBucketRateLimiter implements RateLimiter {
    private static final String KEY_PREFIX = "rate:";
    private static final String FIELD_NAME_TOKENS = "tokens";
    private static final String FIELD_NAME_LAST_LEAKING_TIME = "lastLeakingTime";
    private final int capacity;
    private final int leakingRate;
    private final Jedis jedis;
    private static final int TOKEN_NUMBER_TO_BE_ADDED = 1;

    @Autowired
    public LeakyBucketRateLimiter(
            @Value("10") int capacity,
            @Value("10") int leakingRate,
            Jedis jedis) {
        this.capacity = capacity;
        this.leakingRate = leakingRate;
        this.jedis = jedis;
    }

    private String getKey(String clientId) {
        return KEY_PREFIX + clientId;
    }

    void initialized(String clientId) {
        if (jedis.exists(KEY_PREFIX + clientId)) {
            return;
        }
        Transaction transaction = jedis.multi();
        transaction.hset(getKey(clientId), FIELD_NAME_TOKENS, String.valueOf(0));
        transaction.hset(getKey(clientId), FIELD_NAME_LAST_LEAKING_TIME, String.valueOf(Instant.now().getEpochSecond()));
        transaction.exec();
    }

    @Override
    public boolean isAllowed(String clientId) {
        initialized(clientId);
        LeakingBucketInfo bucketInfo = new LeakingBucketInfo(jedis.hgetAll(getKey(clientId)));
        long currentTime = Instant.now().getEpochSecond();
        if (calculateTokens(bucketInfo, currentTime) <= capacity) {
            updateTokenInfo(clientId, bucketInfo, currentTime);
            return true;
        }
        return false;
    }

    private void updateTokenInfo(String clientId, LeakingBucketInfo bucketInfo, long currentTime) {
        Transaction transaction = jedis.multi();
        transaction.hset(getKey(clientId), FIELD_NAME_TOKENS,
                String.valueOf(getNewTokenNumber(bucketInfo, currentTime)));
        if (needLeaking(currentTime, bucketInfo.lastLeakingTime)) {
            transaction.hset(getKey(clientId), FIELD_NAME_LAST_LEAKING_TIME, String.valueOf(currentTime));
        }
        transaction.exec();
    }

    private int calculateTokens(LeakingBucketInfo bucketInfo, long currentTime) {
        int tokensToBeLeaked = needLeaking(currentTime, bucketInfo.getLastLeakingTime()) ? leakingRate : 0;
        return Math.max(0, bucketInfo.getTokens() - tokensToBeLeaked) + TOKEN_NUMBER_TO_BE_ADDED;
    }

    private int getNewTokenNumber(LeakingBucketInfo bucketInfo, long currentTime) {
        int newTokens = calculateTokens(bucketInfo, currentTime);
        return newTokens >= 0 ? Math.min(newTokens, capacity) : 0;
    }

    private boolean needLeaking(long currentTime, long lastLeakingTime) {
        return (currentTime - lastLeakingTime) / 60 > 0;
    }

    private static class LeakingBucketInfo {
        private final int tokens;
        private final long lastLeakingTime;

        public LeakingBucketInfo(Map<String, String> bucketData) {
            this.tokens = Integer.parseInt(bucketData.get(FIELD_NAME_TOKENS));
            this.lastLeakingTime = Long.parseLong(bucketData.get(FIELD_NAME_LAST_LEAKING_TIME));
        }

        public int getTokens() {
            return tokens;
        }

        public long getLastLeakingTime() {
            return lastLeakingTime;
        }
    }
}
