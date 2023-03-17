package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import java.util.List;

/** The interface Customer service. */
public interface CustomerService {
  /**
   * Find all list.
   *
   * @return the list
   */
  List<CustomerDto> findAll();

  /**
   * Create customer dto.
   *
   * @param dto the dto
   * @return the customer dto
   */
  CustomerDto create(CustomerDto dto);

  /**
   * Update customer dto.
   *
   * @param id the id
   * @param dto the dto
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  CustomerDto update(int id, CustomerDto dto) throws CustomerNotFoundException;

  /**
   * Delete.
   *
   * @param id the id
   * @throws CustomerNotFoundException the customer not found exception
   */
  void deleteById(int id) throws CustomerNotFoundException;

  /**
   * Find by id customer dto.
   *
   * @param id the id
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  CustomerDto findById(int id) throws CustomerNotFoundException;
}
