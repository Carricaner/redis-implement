package org.example.core.domain.error;

import java.util.List;

public class ErrorCodeFactory {

  public static final class TooManyRequestErrorCode extends ErrorCode {
    private static final String NAME = "Too many requests error";
    private static final String MESSAGE = "There are too many requests within a certain period.";

    public TooManyRequestErrorCode() {
      super(NAME, MESSAGE);
    }

    public TooManyRequestErrorCode(List<ErrorCodeDetail> details) {
      super(NAME, MESSAGE, details);
    }
  }

  public static final class UnexpectedErrorCode extends ErrorCode {
    private static final String NAME = "Unexpected Error";
    private static final String MESSAGE = "The exception is unexpected.";

    public UnexpectedErrorCode(List<ErrorCodeDetail> details) {
      super(NAME, MESSAGE, details);
    }
  }
}
