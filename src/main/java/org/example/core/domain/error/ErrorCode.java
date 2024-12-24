package org.example.core.domain.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ErrorCode implements Serializable {
  private final String name;
  private final String message;
  private List<ErrorCodeDetail> details;

  public ErrorCode(String name, String message) {
    this.name = name;
    this.message = message;
    this.details = new ArrayList<>();
  }

  public ErrorCode(String name, String message, List<ErrorCodeDetail> details) {
    this.name = name;
    this.message = message;
    this.details = details;
  }
}
