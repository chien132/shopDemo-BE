package chien.demo.shopdemo.dto;

import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

/** The type Order dto. */
@Data
public class OrderDto implements Serializable {
  private int id;
  private CustomerDto customer;
  private Date orderDate;
}
