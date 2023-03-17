package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Customer dto. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
  private int id;
  private String username;
  private String password;
  private boolean type;
}
