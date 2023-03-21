package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.CartDto;
import java.util.List;

/** The interface Cart service. */
public interface CartService {
  /**
   * Find all list.
   *
   * @return the list
   */
  List<CartDto> findAll();

  /**
   * Create cart dto.
   *
   * @param dto the dto
   * @return the cart dto
   */
  CartDto create(CartDto dto);

  /**
   * Update cart dto.
   *
   * @param id the id
   * @param dto the dto
   * @return the cart dto
   */
  CartDto update(int id, CartDto dto);

  /**
   * Delete.
   *
   * @param id the id
   */
  void deleteById(int id);

  /**
   * Find by id cart dto.
   *
   * @param id the id
   * @return the cart dto
   */
  CartDto findById(int id);

  /**
   * Find cart by customer id.
   *
   * @param id the id
   * @return the cart dto
   */
  CartDto findByCustomerId(int id);
}
