package chien.demo.shopdemo.controller;

import chien.demo.shopdemo.dto.CartDetailDto;
import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.service.CartDetailService;
import chien.demo.shopdemo.service.CartService;
import chien.demo.shopdemo.service.CustomerService;
import chien.demo.shopdemo.service.ItemService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
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

/** Cart controller. */
@RestController
@RequestMapping("/api/carts")
public class CartController {
  private final CartService cartService;
  private final CustomerService customerService;
  private final ItemService itemService;
  private final CartDetailService cartDetailService;

  /**
   * Instantiates a new Cart controller.
   *
   * @param cartService the cart service
   * @param customerService the customer service
   * @param itemService the item service
   * @param cartDetailService the cart detail service
   */
  @Autowired
  public CartController(
      CartService cartService,
      CustomerService customerService,
      ItemService itemService,
      CartDetailService cartDetailService) {
    this.cartService = cartService;
    this.customerService = customerService;
    this.itemService = itemService;
    this.cartDetailService = cartDetailService;
  }
  /**
   * Gets customer's cart by customer id.
   *
   * @param id customer id
   * @return the customer's cart
   */

  @GetMapping("/{id}")
  public ResponseEntity<CartDto> getCustomersCart(@PathVariable("id") int id)
      throws CustomerNotFoundException {
    CartDto foundCart = cartService.findByCustomerId(id);
    if (foundCart != null) {
      return ResponseEntity.ok().body(foundCart);
    } else {
      CustomerDto customer = customerService.findById(id);
      CartDto cartDto = new CartDto().setCustomer(customer);
      CartDto createdCart = cartService.create(cartDto);
      return ResponseEntity.ok(createdCart);
    }
  }

  /**
   * Add item to cart.
   *
   * @param values the values map {itemId, customerId, quantity}
   * @return the Cart of customer with specified id
   * @throws CustomerNotFoundException if customer is not found
   */
  @PostMapping
  public ResponseEntity<CartDto> addItem(@RequestBody Map<String, String> values)
      throws CustomerNotFoundException {
    ItemDto item = itemService.findById(Integer.parseInt(values.get("itemId")));
    int quantity = Integer.parseInt(values.get("quantity"));
    int customerid = Integer.parseInt(values.get("customerId"));
    CustomerDto customerDto = customerService.findById(customerid);
    CartDto foundCart = cartService.findByCustomerId(customerDto.getId());
    if (foundCart == null) {
      foundCart = cartService.create(new CartDto(0, customerDto, new ArrayList<>()));
    }
    for (CartDetailDto c : foundCart.getCartDetails()) {
      if (c.getItem().getId() == item.getId()) {
        if ((c.getQuantity() + quantity) < 1) {
          deleteItem(c.getId());
          foundCart.getCartDetails().remove(c);
          return ResponseEntity.ok(foundCart);
        }
        c.setQuantity(c.getQuantity() + quantity);
        cartDetailService.update(c.getId(), c);
        return ResponseEntity.ok(cartService.findByCustomerId(customerid));
      }
    }
    CartDetailDto cartDetail =
        new CartDetailDto(0, foundCart.getId(), item, quantity, LocalDate.now());
    foundCart.getCartDetails().add(cartDetailService.create(cartDetail));
    return ResponseEntity.ok(foundCart);
  }

  /**
   * Update item.
   *
   * @param values the values map {cartDetailId, quantity}
   * @return status
   */
  @PutMapping
  public ResponseEntity<HttpStatus> updateItem(@RequestBody Map<String, String> values) {
    int quantity = Integer.parseInt(values.get("quantity"));
    CartDetailDto cartDetailDto =
        cartDetailService.findById(Integer.parseInt(values.get("cartDetailId")));
    cartDetailDto.setQuantity(quantity);
    cartDetailService.create(cartDetailDto);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Delete item.
   *
   * @param id cart detail id
   * @return status
   */
  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") int id) {
    cartDetailService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
