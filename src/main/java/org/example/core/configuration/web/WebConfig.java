package org.example.core.configuration.web;

import org.example.core.configuration.web.interceptor.ApiMetricInterceptor;
import org.example.core.configuration.web.interceptor.RateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private final RateLimitInterceptor rateLimitInterceptor;
  private final ApiMetricInterceptor apiMetricInterceptor;
  private final String pathPrefix;

  @Autowired
  public WebConfig(
      RateLimitInterceptor rateLimitInterceptor,
      ApiMetricInterceptor apiMetricInterceptor,
      @Value("${server.apiVersion}") String apiVersion) {
    this.rateLimitInterceptor = rateLimitInterceptor;
    this.apiMetricInterceptor = apiMetricInterceptor;
    this.pathPrefix = "/" + apiVersion + "/api";
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(rateLimitInterceptor);
    registry.addInterceptor(apiMetricInterceptor);
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix(pathPrefix, c -> true);
  }
}
