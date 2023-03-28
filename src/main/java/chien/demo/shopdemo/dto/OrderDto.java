package chien.demo.shopdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** The type Order dto. */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
  private int id;
  private CustomerDto customer;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private Date orderDate;
  private List<OrderDetailDto> orderDetails;
}
