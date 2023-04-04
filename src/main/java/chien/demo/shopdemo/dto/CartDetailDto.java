package chien.demo.shopdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
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

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Etc/GMT+7")
  private LocalDate dateAdded;
}
