package org.example.core.configuration.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import org.example.core.domain.ratelimiter.RateLimiterManager;
import org.example.core.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

  private final RateLimiterManager rateLimiterManager;

  @Autowired
  public RateLimitInterceptor(RateLimiterManager rateLimiterManager) {
    this.rateLimiterManager = rateLimiterManager;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String ipAddress = WebUtils.getIpAddress(request);
    Instant now = Instant.now();
    if (!rateLimiterManager.getRateLimiter().isAllowed(ipAddress, now)) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("Exceed the request number limit in a minute.");
      return false;
    }
    return true;
  }
}
