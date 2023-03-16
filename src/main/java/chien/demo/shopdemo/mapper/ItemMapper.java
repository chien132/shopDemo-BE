package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.model.Item;

/** The type Item mapper. */
public class ItemMapper {

  /** The constant INSTANCE. */
  private static ItemMapper instance;

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static ItemMapper getInstance() {
    if (instance == null) {
      instance = new ItemMapper();
    }
    return instance;
  }

  /**
   * To entity item.
   *
   * @param itemDto the item dto
   * @return the item
   */
  public Item toEntity(ItemDto itemDto) {
    Item item = new Item();
    item.setId(itemDto.getId());
    item.setName(itemDto.getName());
    item.setPrice(itemDto.getPrice());
    return item;
  }

  /**
   * To dto item dto.
   *
   * @param item the item
   * @return the item dto
   */
  public ItemDto toDto(Item item) {
    ItemDto itemDto = new ItemDto();
    itemDto.setId(item.getId());
    itemDto.setName(item.getName());
    itemDto.setPrice(item.getPrice());
    return itemDto;
  }
}
