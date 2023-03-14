package chien.demo.shopdemo.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private int id;
    private String username;
    private String password;
    private boolean type;
}
