package org.example.core.configuration.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import org.example.core.domain.ratelimiter.RateLimited;
import org.example.core.domain.ratelimiter.RateLimiterManager;
import org.example.core.domain.ratelimiter.error.RateLimitExceededException;
import org.example.core.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
  private final RateLimiterManager rateLimiterManager;

  @Autowired
  public RateLimitInterceptor(RateLimiterManager rateLimiterManager) {
    this.rateLimiterManager = rateLimiterManager;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (handler instanceof HandlerMethod handlerMethod) {
      boolean rateLimitedOnMethodLevel =
          handlerMethod.getMethodAnnotation(RateLimited.class) != null;
      RateLimited rateLimited =
          rateLimitedOnMethodLevel
              ? handlerMethod.getMethodAnnotation(RateLimited.class)
              : handlerMethod.getBeanType().getAnnotation(RateLimited.class);
      if (rateLimited != null
          && !rateLimiterManager.tryAcquire(WebUtils.getIpAddress(request), Instant.now())) {
        throw new RateLimitExceededException();
      }
    }
    return true;
  }
}
