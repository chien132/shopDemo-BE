package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.Data;

/** The type Order detail dto. */
@Data
public class OrderDetailDto implements Serializable {
  private int id;
  private OrderDto order;
  private ItemDto item;
  private int quantity;
}
