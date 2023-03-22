package chien.demo.shopdemo.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/** The type Signup request. */
@Getter
@Setter
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(min = 3, max = 40)
  private String password;

  private boolean role;
}
