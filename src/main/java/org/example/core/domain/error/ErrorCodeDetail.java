package org.example.core.domain.error;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class ErrorCodeDetail implements Serializable {
  private final String message;
  private final String field;
  private final String value;

  public ErrorCodeDetail(String message, String field, String value) {
    this.message = message;
    this.field = field;
    this.value = value;
  }

  public ErrorCodeDetail(String message, String field) {
    this.message = message;
    this.field = field;
    this.value = "";
  }
}
