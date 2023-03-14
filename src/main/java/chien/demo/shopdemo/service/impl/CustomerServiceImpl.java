package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.repository.CustomerRepository;
import chien.demo.shopdemo.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(int id, Customer customer) {
        Customer dbCustomer = customerRepository.findById(id);
        dbCustomer.setUsername(customer.getUsername());
        dbCustomer.setPassword(customer.getPassword());
        dbCustomer.setType(customer.isType());
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id);
        customerRepository.delete(customer);
    }

    @Override
    public Customer getCustomerById(int id) {
        Customer result = customerRepository.findById(id);
        return result;
    }
}
