package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.repository.CustomerRepository;
import chien.demo.shopdemo.service.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Customer service. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomerServiceImpl implements CustomerService {
  @Autowired private CustomerRepository customerRepository;

  @Override
  public List<CustomerDto> findAll() {
    return customerRepository.findAll().stream()
        .map(customer -> CustomerMapper.getInstance().toDto(customer))
        .collect(Collectors.toList());
  }

  @Override
  public CustomerDto create(CustomerDto dto) {
    Customer customer = CustomerMapper.getInstance().toEntity(dto);
    return CustomerMapper.getInstance().toDto(customerRepository.save(customer));
  }

  @Override
  public CustomerDto update(int id, CustomerDto dto) throws CustomerNotFoundException {
    Customer dbCustomer =
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    dbCustomer.setUsername(dto.getUsername());
    dbCustomer.setPassword(dto.getPassword());
    dbCustomer.setType(dto.isType());
    return CustomerMapper.getInstance().toDto(customerRepository.save(dbCustomer));
  }

  @Override
  public void delete(int id) throws CustomerNotFoundException {
    Customer customer =
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    customerRepository.delete(customer);
  }

  @Override
  public CustomerDto findById(int id) throws CustomerNotFoundException {
    Optional<Customer> result = customerRepository.findById(id);
    if (result.isPresent()) {
      return CustomerMapper.getInstance().toDto(result.get());
    } else {
      throw new CustomerNotFoundException(id);
    }
  }
}
