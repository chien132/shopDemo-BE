package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Cart dto. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {
  private int id;
  private CustomerDto customer;
}
