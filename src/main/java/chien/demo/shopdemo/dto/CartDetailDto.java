package chien.demo.shopdemo.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** The type Cart detail dto. */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDto implements Serializable {
  private int id;
  private int cartId;
  private ItemDto item;
  private int quantity;
  private Date dateAdded;
}
