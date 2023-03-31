package chien.demo.shopdemo.controller;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.ItemCascadeDeleteError;
import chien.demo.shopdemo.exception.ItemNotFoundException;
import chien.demo.shopdemo.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Item controller. */
@RestController
@RequestMapping("/api/items")
public class ItemController {
  private final ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }
  /**
   * Gets all items.
   *
   * @return all items
   */

  @GetMapping
  public ResponseEntity<List<ItemDto>> getAllItems() {
    return ResponseEntity.ok(itemService.findAll());
  }

  /**
   * Gets item by id.
   *
   * @param id the id
   * @return the item by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ItemDto> getItemById(@PathVariable("id") int id) {
    ItemDto foundItem = itemService.findById(id);
    if (foundItem != null) {
      return ResponseEntity.ok(foundItem);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Create item response entity.
   *
   * @param requestItem the request item
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto requestItem) {
    ItemDto savedItem = itemService.create(requestItem);
    return ResponseEntity.ok(savedItem);
  }

  /**
   * Update item response entity.
   *
   * @param requestItem the request item
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto requestItem)
      throws ItemNotFoundException {
    ItemDto savedItem = itemService.update(requestItem.getId(), requestItem);
    return ResponseEntity.ok(savedItem);
  }

  /**
   * Delete item response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") int id)
      throws ItemCascadeDeleteError, ItemNotFoundException {
    String result = itemService.deleteById(id);
    if (result.equals("deleted")) {
      return ResponseEntity.ok().build();
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
