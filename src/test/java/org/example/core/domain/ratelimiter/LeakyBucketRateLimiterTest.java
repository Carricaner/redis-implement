package org.example.core.domain.ratelimiter;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LeakyBucketRateLimiterTest {
//    @Mock
//    private BucketRateLimiterAdapter bucketRateLimiterAdapter;

//    @Test
//    void testIs_Allowed_Return_True() {
//        // Arrange
//        LeakyBucketRateLimiter leakyBucketRateLimiter = new LeakyBucketRateLimiter(
//                10L ,10L, bucketRateLimiterAdapter);
//        String clientId = "client-Id";
//        Instant now = Instant.now();
//        TokenBucket tokenBucket = new TokenBucket(
//                0L, now.minusSeconds(20L).getEpochSecond());
//        when(bucketRateLimiterAdapter.findTokenBucket(any())).thenReturn(Optional.of(tokenBucket));
//
//        // Act
//        boolean result = leakyBucketRateLimiter.isAllowed(clientId, now);
//
//        // Assert
//        Assertions.assertThat(result).isTrue();
//        verify(bucketRateLimiterAdapter, times(1)).updateBucketInfo(any(), any());
//    }
//
//    @Test
//    void testIs_Allowed_Return_False() {
//        // Arrange
//        LeakyBucketRateLimiter leakyBucketRateLimiter = new LeakyBucketRateLimiter(
//                10L ,10L, bucketRateLimiterAdapter);
//        String clientId = "client-Id";
//        Instant now = Instant.now();
//        TokenBucket tokenBucket = new TokenBucket(
//                10L, now.minusSeconds(20L).getEpochSecond());
//        when(bucketRateLimiterAdapter.findTokenBucket(any())).thenReturn(Optional.of(tokenBucket));
//
//        // Act
//        boolean result = leakyBucketRateLimiter.isAllowed(clientId, now);
//
//        // Assert
//        Assertions.assertThat(result).isFalse();
//        verify(bucketRateLimiterAdapter, times(0)).updateBucketInfo(any(), any());
//    }
//
//    @Test
//    void testRefresh_all_OK() {
//        // Arrange
//        LeakyBucketRateLimiter leakyBucketRateLimiter = new LeakyBucketRateLimiter(
//                10L ,10L, bucketRateLimiterAdapter);
//        String clientId = "client-Id";
//
//        // Act
//        leakyBucketRateLimiter.refreshAll(clientId);
//
//        // Assert
//        verify(bucketRateLimiterAdapter, times(1)).resetAllRecords(any());
//    }
}
