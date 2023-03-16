package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.Data;

/** The type Cart dto. */
@Data
public class CartDto implements Serializable {
  private int id;
  private CustomerDto customer;
}
