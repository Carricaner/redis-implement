package org.example.core.configuration.web.handler;

import java.util.List;
import org.example.core.domain.error.ErrorCodeDetail;
import org.example.core.domain.error.ErrorCodeFactory.TooManyRequestErrorCode;
import org.example.core.domain.ratelimiter.error.RateLimitExceededException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class RateLimitExceptionHandler {
  @ExceptionHandler(RateLimitExceededException.class)
  @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
  public TooManyRequestErrorCode handleRateLimitExceededException(
      RateLimitExceededException exception) {
    TooManyRequestErrorCode errorCode = new TooManyRequestErrorCode();
    errorCode.setDetails(List.of(new ErrorCodeDetail(exception.getMessage(), "")));
    return errorCode;
  }
}
