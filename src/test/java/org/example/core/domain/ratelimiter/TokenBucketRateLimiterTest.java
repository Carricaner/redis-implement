package org.example.core.domain.ratelimiter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.core.domain.ratelimiter.port.BucketRateLimiterAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenBucketRateLimiterTest {
    @Mock
    private BucketRateLimiterAdapter bucketRateLimiterAdapter;

    @Test
    void testIs_Allowed_Return_True() {
        
    }

    @Test
    void testIs_Allowed_Return_False() {
        
    }

    @Test
    void testRefresh_all_OK() {
        
    }
}
