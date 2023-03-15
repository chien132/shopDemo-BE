package chien.demo.shopdemo.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class CartDetailDTO implements Serializable {
    private int id;
    private CartDTO cart;
    private ItemDTO item;
    private int quantity;
    private Date dateAdded;
}