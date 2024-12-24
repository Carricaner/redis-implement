package org.example.core.configuration.web.handler;

import java.util.List;
import org.example.core.domain.error.ErrorCodeDetail;
import org.example.core.domain.error.ErrorCodeFactory.UnexpectedErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(2)
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public UnexpectedErrorCode handleUnexpectedException(RuntimeException exception) {
    return new UnexpectedErrorCode(List.of(new ErrorCodeDetail(exception.getMessage(), "")));
  }
}
