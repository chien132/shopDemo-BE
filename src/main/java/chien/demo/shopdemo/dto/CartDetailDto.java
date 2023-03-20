package chien.demo.shopdemo.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Cart detail dto. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDto implements Serializable {
  private int id;
  private CartDto cart;
  private ItemDto item;
  private int quantity;
  private Date dateAdded;
}
