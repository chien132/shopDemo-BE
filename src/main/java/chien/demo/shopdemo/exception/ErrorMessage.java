package chien.demo.shopdemo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/** The type Error message. */
@Data
@AllArgsConstructor
public class ErrorMessage {
  private int statusCode;
  private String message;
}
