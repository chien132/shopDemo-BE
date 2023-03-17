package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Order detail dto. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto implements Serializable {
  private int id;
  private OrderDto order;
  private ItemDto item;
  private int quantity;
}
