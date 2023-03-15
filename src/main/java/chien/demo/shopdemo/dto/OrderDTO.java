package chien.demo.shopdemo.dto;


import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrderDTO implements Serializable {
    private int id;
    private CustomerDTO customer;
    private Date orderDate;
}