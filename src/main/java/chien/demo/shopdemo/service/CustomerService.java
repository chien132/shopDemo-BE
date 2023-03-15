package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.CustomerDTO;
import chien.demo.shopdemo.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();

    CustomerDTO create(CustomerDTO dto);

    CustomerDTO update(int id, CustomerDTO dto) throws CustomerNotFoundException;

    void delete(int id) throws CustomerNotFoundException;

    CustomerDTO findById(int id) throws CustomerNotFoundException;

}
