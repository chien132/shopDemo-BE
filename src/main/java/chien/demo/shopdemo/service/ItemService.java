package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.ItemDto;
import java.util.List;

/** The interface Item service. */
public interface ItemService {
  /**
   * Find all list.
   *
   * @return the list
   */
  List<ItemDto> findAll();

  /**
   * Create item dto.
   *
   * @param dto the dto
   * @return the item dto
   */
  ItemDto create(ItemDto dto);

  /**
   * Update item dto.
   *
   * @param id the id
   * @param dto the dto
   * @return the item dto
   */
  ItemDto update(int id, ItemDto dto);

  /**
   * Delete.
   *
   * @param id the id
   */
  boolean deleteById(int id);

  /**
   * Find by id item dto.
   *
   * @param id the id
   * @return the item dto
   */
  ItemDto findById(int id);
}
