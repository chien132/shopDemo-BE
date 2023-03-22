package chien.demo.shopdemo.payload.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/** The type Login request. */
@Getter
@Setter
public class LoginRequest {
  @NotBlank private String username;

  @NotBlank private String password;
}
