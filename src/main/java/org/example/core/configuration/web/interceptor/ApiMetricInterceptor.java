package org.example.core.configuration.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.core.configuration.web.ApiMetricsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiMetricInterceptor implements HandlerInterceptor {

  private final ApiMetricsService apiMetricsService;

  public ApiMetricInterceptor(ApiMetricsService apiMetricsService) {
    this.apiMetricsService = apiMetricsService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    request.setAttribute("startTime", System.currentTimeMillis());
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    long startTime = (Long) request.getAttribute("startTime");
    long responseTime = System.currentTimeMillis() - startTime;
    String endpoint = request.getRequestURI();
    apiMetricsService.addApiCall(endpoint, responseTime);
  }
}
