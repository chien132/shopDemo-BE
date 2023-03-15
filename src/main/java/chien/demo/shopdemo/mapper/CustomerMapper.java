package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.CustomerDTO;
import chien.demo.shopdemo.model.Customer;

public class CustomerMapper {

    public static CustomerMapper INSTANCE;

    public static CustomerMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerMapper();
        }
        return INSTANCE;
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setType(customerDTO.isType());
        return customer;
    }
    public CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setType(customer.isType());
        return customerDTO;
    }

}
