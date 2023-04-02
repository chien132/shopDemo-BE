package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.ItemCascadeDeleteError;
import chien.demo.shopdemo.exception.ItemNotFoundException;
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
  ItemDto update(int id, ItemDto dto) throws ItemNotFoundException;

  /**
   * Delete.
   *
   * @param id the id
   */
  String deleteById(int id) throws ItemCascadeDeleteError, ItemNotFoundException;

  /**
   * Find by id item dto.
   *
   * @param id the id
   * @return the item dto
   */
  ItemDto findById(int id);

  /**
   * Find all items with name like input string.
   *
   * @param search the search string
   * @return the item list
   */
  List<ItemDto> findAllByNameLike(String search);
}
