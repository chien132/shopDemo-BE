package chien.demo.shopdemo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemDTO implements Serializable {
    private int id;
    private String name;
    private double price;
}