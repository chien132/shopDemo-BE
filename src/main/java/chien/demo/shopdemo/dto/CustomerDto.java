package chien.demo.shopdemo.dto;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** The type Customer dto. */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
  private int id;
  @NotBlank private String username;
  @NotBlank private String password;
  private boolean type;
}
