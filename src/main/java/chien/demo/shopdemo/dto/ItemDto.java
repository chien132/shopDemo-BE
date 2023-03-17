package chien.demo.shopdemo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Item dto. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto implements Serializable {
  private int id;
  private String name;
  private double price;
}
