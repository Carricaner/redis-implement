package org.example.core.domain.ratelimiter;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.example.core.domain.ratelimiter.port.WindowRateLimiterAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SlidingWindowRateLimiterTest {

    @Mock
    private WindowRateLimiterAdapter windowRateLimiterAdapter;

    @Test
    void testIs_Allowed_Return_True() {
        // Arrange
        SlidingWindowRateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(
                10L ,60L ,windowRateLimiterAdapter);
        String clientId = "client-Id";
        Instant now = Instant.now();
        when(windowRateLimiterAdapter.countBetween(any(), anyLong(), anyLong())).thenReturn(1L);

        // Act
        boolean result = slidingWindowRateLimiter.isAllowed(clientId, now);

        // Assert
        Assertions.assertThat(result).isTrue();
        verify(windowRateLimiterAdapter, times(1)).plusOneVisit(any(),any());
    }

    @Test
    void testIs_Allowed_Return_False() {
        // Arrange
        SlidingWindowRateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(
                10L ,60L ,windowRateLimiterAdapter);
        String clientId = "client-Id";
        Instant now = Instant.now();
        when(windowRateLimiterAdapter.countBetween(any(), anyLong(), anyLong()))
                .thenReturn(10L);

        // Act
        boolean result = slidingWindowRateLimiter.isAllowed(clientId, now);

        // Assert
        Assertions.assertThat(result).isFalse();
        verify(windowRateLimiterAdapter, times(0)).plusOneVisit(any(),any());
    }

    @Test
    void testRefresh_all_OK() {
        // Arrange
        SlidingWindowRateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(
                10L ,60L ,windowRateLimiterAdapter);
        String clientId = "client-Id";

        // Act
        slidingWindowRateLimiter.refreshAll(clientId);

        // Assert
        verify(windowRateLimiterAdapter, times(1)).resetAllRecords(any());
    }
}