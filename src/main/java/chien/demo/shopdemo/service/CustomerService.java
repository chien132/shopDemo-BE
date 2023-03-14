package chien.demo.shopdemo.service;

import chien.demo.shopdemo.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer createCustomer(Customer customer);

    Customer updateCustomer(int id, Customer customer);

    void deleteCustomer(int id);

    Customer getCustomerById(int id);

}
