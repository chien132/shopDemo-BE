package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.Data;

/** The type Item dto. */
@Data
public class ItemDto implements Serializable {
  private int id;
  private String name;
  private double price;
}
