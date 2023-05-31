package org.example.core.configuration.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.domain.ratelimiter.RateLimiterClient;
import org.example.core.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterClient rateLimiterClient;

    @Autowired
    public RateLimitInterceptor(RateLimiterClient rateLimiterClient) {
        this.rateLimiterClient = rateLimiterClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = WebUtils.getIpAddress(request);
        Instant now = Instant.now();
        if (!rateLimiterClient.getRateLimiter().isAllowed(ipAddress, now)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Exceed the request number limit in a minute.");
            return false;
        }
        return true;
    }
}
