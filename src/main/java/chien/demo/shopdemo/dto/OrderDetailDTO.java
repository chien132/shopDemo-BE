package chien.demo.shopdemo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailDTO implements Serializable {
    private int id;
    private OrderDTO order;
    private ItemDTO item;
    private int quantity;
}