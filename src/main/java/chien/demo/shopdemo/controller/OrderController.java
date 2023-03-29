package chien.demo.shopdemo.controller;

import chien.demo.shopdemo.dto.CartDetailDto;
import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.dto.OrderDetailDto;
import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.exception.EmptyCartException;
import chien.demo.shopdemo.service.CartDetailService;
import chien.demo.shopdemo.service.CartService;
import chien.demo.shopdemo.service.OrderDetailService;
import chien.demo.shopdemo.service.OrderService;
import java.time.LocalDate;
import java.util.ArrayList;
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
  private final OrderService orderService;
  private final OrderDetailService orderDetailService;
  private final CartService cartService;
  private final CartDetailService cartDetailService;

  /**
   * Instantiates a new Order controller.
   *
   * @param orderService the order service
   * @param orderDetailService the order detail service
   * @param cartService the cart service
   * @param cartDetailService the cart detail service
   */
  @Autowired
  public OrderController(
      OrderService orderService,
      OrderDetailService orderDetailService,
      CartService cartService,
      CartDetailService cartDetailService) {
    this.orderService = orderService;
    this.orderDetailService = orderDetailService;
    this.cartService = cartService;
    this.cartDetailService = cartDetailService;
  }

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
  public ResponseEntity<OrderDto> createOrderByCustomerId(@RequestBody int customerId)
      throws EmptyCartException {
    CartDto cartDto = cartService.findByCustomerId(customerId);
    if (cartDto == null) {
      return ResponseEntity.notFound().build();
    }
    if (cartDto.getCartDetails().isEmpty()) {
      throw new EmptyCartException();
    }
    OrderDto orderDto = new OrderDto(0, cartDto.getCustomer(), LocalDate.now(), new ArrayList<>());
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
