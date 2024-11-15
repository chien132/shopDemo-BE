package chien.demo.shopdemo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.model.Customer;

/** The type Customer mapper. */
@SuppressWarnings("checkstyle:InterfaceIsType")
@Mapper(componentModel = "spring")
public interface CustomerMapper extends AbstractMapper<Customer, CustomerDto> {

  /** The constant INSTANCE. */
  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
  //
  ///**
  // * Gets instance.
  // *
  // * @return the instance
  // */
  //public static CustomerMapper getInstance() {
  //  if (instance == null) {
  //    instance = new CustomerMapper();
  //  }
  //  return instance;
  //}
  //
  ///**
  // * To entity customer.
  // *
  // * @param customerDto the customer dto
  // * @return the customer
  // */
  //public Customer toEntity(CustomerDto customerDto) {
  //  Customer customer = new Customer();
  //  customer.setId(customerDto.getId());
  //  customer.setUsername(customerDto.getUsername());
  //  customer.setPassword(customerDto.getPassword());
  //  customer.setType(customerDto.isType());
  //  return customer;
  //}
  //
  ///**
  // * To dto customer dto.
  // *
  // * @param customer the customer
  // * @return the customer dto
  // */
  //public CustomerDto toDto(Customer customer) {
  //  CustomerDto customerDto = new CustomerDto();
  //  customerDto.setId(customer.getId());
  //  customerDto.setUsername(customer.getUsername());
  //  customerDto.setPassword(customer.getPassword());
  //  customerDto.setType(customer.isType());
  //  return customerDto;
  //}
}
