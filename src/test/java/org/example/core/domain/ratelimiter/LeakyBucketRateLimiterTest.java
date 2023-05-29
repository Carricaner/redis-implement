package org.example.core.domain.ratelimiter;

import org.example.core.domain.ratelimiter.port.BucketRateLimiterAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LeakyBucketRateLimiterTest {
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
