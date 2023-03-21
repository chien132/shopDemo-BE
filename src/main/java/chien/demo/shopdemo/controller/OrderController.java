package chien.demo.shopdemo.controller;

import chien.demo.shopdemo.dto.CartDetailDto;
import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.dto.OrderDetailDto;
import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.service.CartDetailService;
import chien.demo.shopdemo.service.CartService;
import chien.demo.shopdemo.service.OrderDetailService;
import chien.demo.shopdemo.service.OrderService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Order controller. */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
  @Autowired OrderService orderService;
  @Autowired OrderDetailService orderDetailService;
  @Autowired CartService cartService;
  @Autowired CartDetailService cartDetailService;

  /**
   * Gets orders by customer id.
   *
   * @param id the customer's id
   * @return the orders by customer id
   */
  @GetMapping("{id}")
  public ResponseEntity<List<OrderDto>> getOrdersByCustomerId(@PathVariable(name = "id") int id) {
    return ResponseEntity.ok(orderService.findAllByCustomerId(id));
  }

  /**
   * Create order by customer id.
   *
   * @param customerId the customer id
   * @return the created order
   */
  @PostMapping
  public ResponseEntity<OrderDto> createOrderByCustomerId(@RequestBody int customerId) {
    CartDto cartDto = cartService.findByCustomerId(customerId);
    if (cartDto == null) {
      return ResponseEntity.notFound().build();
    }
    if (cartDto.getCartDetails().isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    OrderDto orderDto = new OrderDto(0, cartDto.getCustomer(), new Date(), new ArrayList<>());
    OrderDto savedOrder = orderService.create(orderDto);
    for (CartDetailDto cartDetailDto : cartDto.getCartDetails()) {
      OrderDetailDto orderDetailDto =
          new OrderDetailDto()
              .setOrderId(savedOrder.getId())
              .setItem(cartDetailDto.getItem())
              .setQuantity(cartDetailDto.getQuantity());
      orderDetailService.create(orderDetailDto);
      savedOrder.getOrderDetails().add(orderDetailDto);
      cartDetailService.deleteById(cartDetailDto.getId());
    }
    cartService.deleteById(cartDto.getId());
    return ResponseEntity.ok(savedOrder);
  }
}
