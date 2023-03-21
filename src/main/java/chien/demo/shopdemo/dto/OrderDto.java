package chien.demo.shopdemo.dto;

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
  private Date orderDate;
  private List<OrderDetailDto> orderDetails;
}
