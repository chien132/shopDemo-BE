package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.Data;

/** The type Customer dto. */
@Data
public class CustomerDto implements Serializable {
  private int id;
  private String username;
  private String password;
  private boolean type;
}
