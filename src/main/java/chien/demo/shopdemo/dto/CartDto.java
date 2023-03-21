package chien.demo.shopdemo.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** The type Cart dto. */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {
  private int id;
  private CustomerDto customer;
  private List<CartDetailDto> cartDetails;
}
