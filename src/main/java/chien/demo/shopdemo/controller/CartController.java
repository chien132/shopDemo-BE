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
import java.util.ArrayList;
import java.util.Date;
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

@RestController
@RequestMapping("/api/carts")
public class CartController {
  @Autowired private CartService cartService;
  @Autowired private CustomerService customerService;
  @Autowired private ItemService itemService;
  @Autowired private CartDetailService cartDetailService;

  @GetMapping("/{id}")
  public ResponseEntity<CartDto> getCustomersCart(@PathVariable("id") int id) {
    CartDto foundCart = cartService.findByCustomerId(id);
    if (foundCart != null) {
      return ResponseEntity.ok().body(foundCart);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

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
        c.setQuantity(c.getQuantity() + quantity);
        CartDetailDto saved = cartDetailService.update(c.getId(), c);
        return ResponseEntity.ok(cartService.findByCustomerId(customerid));
      }
    }
    CartDetailDto cartDetail = new CartDetailDto(0, foundCart.getId(), item, quantity, new Date());
    foundCart.getCartDetails().add(cartDetailService.create(cartDetail));
    return ResponseEntity.ok(foundCart);
  }

  @PutMapping
  public ResponseEntity<HttpStatus> updateItem(@RequestBody Map<String, String> values) {
    int quantity = Integer.parseInt(values.get("quantity"));
    CartDetailDto cartDetailDto =
        cartDetailService.findById(Integer.parseInt(values.get("cartDetailId")));
    cartDetailDto.setQuantity(quantity);
    cartDetailService.create(cartDetailDto);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") int id) {
    cartDetailService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
