package chien.demo.shopdemo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CartDTO implements Serializable {
    private int id;
    private CustomerDTO customer;
}