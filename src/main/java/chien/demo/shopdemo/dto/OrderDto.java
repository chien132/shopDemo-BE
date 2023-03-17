package chien.demo.shopdemo.dto;

import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Order dto. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
  private int id;
  private CustomerDto customer;
  private Date orderDate;
}
