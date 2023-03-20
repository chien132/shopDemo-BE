package chien.demo.shopdemo.testcontroller;

import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.service.CustomerService;
import chien.demo.shopdemo.service.OrderService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The type Test order controller. */
@RestController
@RequestMapping("/api/orders/")
public class TestOrderController {

  /** The Order service. */
  @Autowired OrderService orderService;
  /** The Customer service. */
  @Autowired CustomerService customerService;

  /**
   * Gets customer orders.
   *
   * @param id the id
   * @return the customer orders
   */
  @GetMapping("{id}")
  public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable(name = "id") int id) {
    return ResponseEntity.ok(orderService.findAllByCustomerId(id));
  }

  /**
   * Create order response entity.
   *
   * @return the response entity
   * @throws CustomerNotFoundException the customer not found exception
   */
  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestParam int id, @RequestParam String date)
      throws CustomerNotFoundException, ParseException {
    // Test
    OrderDto order = new OrderDto();
    order.setCustomer(customerService.findById(id));
    //    order.setOrderDate(new Date(System.currentTimeMillis()));
    order.setOrderDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
    // end test
    OrderDto responseOrder = orderService.create(order);
    return new ResponseEntity<>(responseOrder, HttpStatus.CREATED);
  }

  @GetMapping("/find/{id}")
  ResponseEntity<OrderDto> findOrder(@PathVariable("id") int id) {
    OrderDto testDto = orderService.findById(id);
    return new ResponseEntity<>(testDto, HttpStatus.FOUND);
  }
}
