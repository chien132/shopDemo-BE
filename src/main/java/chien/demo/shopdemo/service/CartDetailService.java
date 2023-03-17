package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.CartDetailDto;
import java.util.List;

/** The interface Cart detail service. */
public interface CartDetailService {
  /**
   * Find all list.
   *
   * @return the list
   */
  List<CartDetailDto> findAll();

  /**
   * Create cart detail dto.
   *
   * @param dto the dto
   * @return the cart detail dto
   */
  CartDetailDto create(CartDetailDto dto);

  /**
   * Update cart detail dto.
   *
   * @param id the id
   * @param dto the dto
   * @return the cart detail dto
   */
  CartDetailDto update(int id, CartDetailDto dto);

  /**
   * Delete.
   *
   * @param id the id
   */
  void deleteById(int id);

  /**
   * Find by id cart detail dto.
   *
   * @param id the id
   * @return the cart detail dto
   */
  CartDetailDto findById(int id);
}
