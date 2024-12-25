package org.example.core.configuration.web;

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
  private final String pathPrefix;

  @Autowired
  public WebConfig(
      RateLimitInterceptor rateLimitInterceptor, @Value("${server.apiVersion}") String apiVersion) {
    this.rateLimitInterceptor = rateLimitInterceptor;
    this.pathPrefix = "/" + apiVersion + "/api";
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(rateLimitInterceptor);
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix(pathPrefix, c -> true);
  }
}
