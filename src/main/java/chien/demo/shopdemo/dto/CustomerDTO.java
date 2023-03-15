package chien.demo.shopdemo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDTO implements Serializable {
    private int id;
    private String username;
    private String password;
    private boolean type;
}
