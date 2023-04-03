package chien.demo.shopdemo.dto;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** The type Item dto. */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto implements Serializable {
  private int id;

  @NotBlank private String name;

  @Min(value = 0)
  private double price;
}
