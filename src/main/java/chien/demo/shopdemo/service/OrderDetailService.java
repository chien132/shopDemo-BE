package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.OrderDetailDto;
import java.util.List;

/** The interface Order detail service. */
public interface OrderDetailService {
  /**
   * Find all list.
   *
   * @return the list
   */
  List<OrderDetailDto> findAll();

  /**
   * Create order detail dto.
   *
   * @param dto the dto
   * @return the order detail dto
   */
  OrderDetailDto create(OrderDetailDto dto);

  /**
   * Update order detail dto.
   *
   * @param id the id
   * @param dto the dto
   * @return the order detail dto
   */
  OrderDetailDto update(int id, OrderDetailDto dto);

  /**
   * Delete.
   *
   * @param id the id
   */
  void deleteById(int id);

  /**
   * Find by id order detail dto.
   *
   * @param id the id
   * @return the order detail dto
   */
  OrderDetailDto findById(int id);
}
