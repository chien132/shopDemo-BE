package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.repository.CustomerRepository;
import chien.demo.shopdemo.service.CustomerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Customer service. */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Throwable.class)
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;

  @Override
  public boolean existsByUsername(String username) {
    return customerRepository.existsByUsername(username);
  }

  @Override
  public List<CustomerDto> findAll() {
    return customerRepository.findAll().stream()
        .map(customer -> CustomerMapper.INSTANCE.toDto(customer))
        .collect(Collectors.toList());
  }

  @Override
  public CustomerDto create(CustomerDto dto) {
    Customer customer = CustomerMapper.INSTANCE.toEntity(dto);
    return CustomerMapper.INSTANCE.toDto(customerRepository.save(customer));
  }

  @Override
  public CustomerDto update(int id, CustomerDto dto) throws CustomerNotFoundException {
    Customer dbCustomer =
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    dbCustomer.setUsername(dto.getUsername());
    dbCustomer.setPassword(dto.getPassword());
    dbCustomer.setType(dto.isType());
    return CustomerMapper.INSTANCE.toDto(customerRepository.save(dbCustomer));
  }

  @Override
  public void deleteById(int id) throws CustomerNotFoundException {
    Customer customer =
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    customerRepository.deleteById(customer.getId());
  }

  @Override
  public CustomerDto findById(int id) throws CustomerNotFoundException {
    Customer customer =
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    return CustomerMapper.INSTANCE.toDto(customer);
  }
}
