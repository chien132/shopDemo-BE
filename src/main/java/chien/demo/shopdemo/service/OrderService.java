package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.OrderDto;
import java.util.List;

/** The interface Order service. */
public interface OrderService {
  /**
   * Find all list.
   *
   * @return the list
   */
  List<OrderDto> findAll();

  /**
   * Find all by customer id list.
   *
   * @param id the id
   * @return the list
   */
  List<OrderDto> findAllByCustomerId(int id);

  /**
   * Create order dto.
   *
   * @param dto the dto
   * @return the order dto
   */
  OrderDto create(OrderDto dto);

  /**
   * Update order dto.
   *
   * @param id the id
   * @param dto the dto
   * @return the order dto
   */
  OrderDto update(int id, OrderDto dto);

  /**
   * Delete.
   *
   * @param id the id
   */
  void delete(int id);

  /**
   * Find by id order dto.
   *
   * @param id the id
   * @return the order dto
   */
  OrderDto findById(int id);
}
