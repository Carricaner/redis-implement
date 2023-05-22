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
@Qualifier("token-bucket-rate-limiter")
public class TokenBucketRateLimiter implements RateLimiter {
    private static final String KEY_PREFIX = "rate:";
    private static final String FIELD_NAME_TOKENS = "tokens";
    private static final String FIELD_NAME_LAST_REFILL_TIME = "lastRefillTime";

    private final int capacity;
    private final int refillRate;
    private static final int TOKEN_NUMBER_TO_BE_CONSUMED = 1;
    private final Jedis jedis;

    @Autowired
    public TokenBucketRateLimiter(
            @Value("10") int capacity, // At most 10 tokens stored in the bucket at a time
            @Value("10") int refillRate, // 10 tokens per minute
            Jedis jedis) {
        this.capacity = capacity;
        this.refillRate = refillRate;
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
        transaction.hset(getKey(clientId), FIELD_NAME_TOKENS, String.valueOf(capacity));
        transaction.hset(getKey(clientId), FIELD_NAME_LAST_REFILL_TIME, String.valueOf(Instant.now().getEpochSecond()));
        transaction.exec();
    }

    @Override
    public boolean isAllowed(String clientId) {
        initialized(clientId);
        TokenBucketInfo bucketInfo = getTokenBucketInfo(clientId);
        long currentTime = Instant.now().getEpochSecond();
        if (calculateTokens(bucketInfo, currentTime) >= 0) {
            updateTokenInfo(clientId, bucketInfo, currentTime);
            return true;
        }
        return false;
    }

    private int calculateTokens(TokenBucketInfo bucketInfo, long currentTime) {
        int tokensToBeAdded = needRefill(currentTime, bucketInfo.getLastRefillTime()) ? refillRate : 0;
        return Math.min(capacity, bucketInfo.getTokens() + tokensToBeAdded) - TOKEN_NUMBER_TO_BE_CONSUMED;
    }

    private void updateTokenInfo(String clientId, TokenBucketInfo bucketInfo, long currentTime) {
        Transaction transaction = jedis.multi();
        transaction.hset(getKey(clientId), FIELD_NAME_TOKENS,
                String.valueOf(getNewTokenNumber(bucketInfo, currentTime)));
        if (needRefill(currentTime, bucketInfo.getLastRefillTime())) {
            transaction.hset(getKey(clientId), FIELD_NAME_LAST_REFILL_TIME, String.valueOf(currentTime));
        }
        transaction.exec();
    }

    private boolean needRefill(long currentTime, long lastRefillTime) {
        return (currentTime - lastRefillTime) / 60 > 0;
    }

    private int getNewTokenNumber(TokenBucketInfo bucketInfo, long currentTime) {
        int newTokens = calculateTokens(bucketInfo, currentTime);
        return newTokens >= 0 ? Math.min(newTokens, capacity) : 0;
    }

    private TokenBucketInfo getTokenBucketInfo(String clientId) {
        return new TokenBucketInfo(jedis.hgetAll(getKey(clientId)));
    }

    private static class TokenBucketInfo {
        private final int tokens;
        private final long lastRefillTime;

        public TokenBucketInfo(Map<String, String> bucketData) {
            this.tokens = Integer.parseInt(bucketData.get(FIELD_NAME_TOKENS));
            this.lastRefillTime = Long.parseLong(bucketData.get(FIELD_NAME_LAST_REFILL_TIME));
        }

        public int getTokens() {
            return tokens;
        }

        public long getLastRefillTime() {
            return lastRefillTime;
        }
    }
}
