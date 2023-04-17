package chien.demo.shopdemo.controller;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.ItemCascadeDeleteError;
import chien.demo.shopdemo.exception.ItemNotFoundException;
import chien.demo.shopdemo.service.ItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("search")
  public ResponseEntity<List<ItemDto>> getAllItemsByNameLike(@RequestParam String search) {
    return ResponseEntity.ok(itemService.findAllByNameLike(search));
  }

  /**
   * Search paginated response entity.
   *
   * @param search the search string
   * @param page the page number
   * @param size the page size
   * @param sort the sort type
   * @return the response entity
   */
  @GetMapping("searchPaginated")
  public ResponseEntity<Map<String, Object>> searchPaginated(
      @RequestParam String search,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "0") int size,
      @RequestParam(defaultValue = "iddesc") String sort) {
    try {
      Sort sortable = Sort.by("id").descending();
      if (sort.equals("idasc")) {
        sortable = Sort.by("id").ascending();
      } else if (sort.equals("nameasc")) {
        sortable = Sort.by("name").ascending();
      } else if (sort.equals("namedesc")) {
        sortable = Sort.by("name").descending();
      } else if (sort.equals("priceasc")) {
        sortable = Sort.by("price").ascending();
      } else if (sort.equals("pricedesc")) {
        sortable = Sort.by("price").descending();
      }

      List<ItemDto> items;
      Pageable paging = PageRequest.of(page, size, sortable);

      Page<ItemDto> pageItems = itemService.findAllPaginated(search, paging);
      items = pageItems.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("items", items);
      response.put("totalItems", pageItems.getTotalElements());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
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
  @PreAuthorize("hasRole('OWNER')")
  public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto requestItem) {
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
  @PreAuthorize("hasRole('OWNER')")
  public ResponseEntity<ItemDto> updateItem(@Valid @RequestBody ItemDto requestItem)
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
  @PreAuthorize("hasRole('OWNER')")
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
