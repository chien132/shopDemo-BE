package chien.demo.shopdemo.dto;

import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

/** The type Cart detail dto. */
@Data
public class CartDetailDto implements Serializable {
  private int id;
  private CartDto cart;
  private ItemDto item;
  private int quantity;
  private Date dateAdded;
}
