package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CustomerDTO;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.repository.CustomerRepository;
import chien.demo.shopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream().map(customer ->
                CustomerMapper.getInstance().toDTO(customer)).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        Customer customer = CustomerMapper.getInstance().toEntity(dto);
        return CustomerMapper.getInstance().toDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO update(int id, CustomerDTO dto) throws CustomerNotFoundException {
        Customer dbCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        dbCustomer.setUsername(dto.getUsername());
        dbCustomer.setPassword(dto.getPassword());
        dbCustomer.setType(dto.isType());
        return CustomerMapper.getInstance().toDTO(customerRepository.save(dbCustomer));
    }

    @Override
    public void delete(int id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(customer);
    }

    @Override
    public CustomerDTO findById(int id) throws CustomerNotFoundException {
        Optional<Customer> result = customerRepository.findById(id);
        if (result.isPresent()) {
            return CustomerMapper.getInstance().toDTO(result.get());
        } else {
            throw new CustomerNotFoundException(id);
        }
//        return CustomerMapper.INSTANCE.toDTO(customerRepository.findById(id).get());
    }
}
