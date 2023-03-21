package chien.demo.shopdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.model.Order;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class OrderRepositoryTest {
  @Autowired OrderRepository orderRepository;
  @Autowired CustomerRepository customerRepository;

  Customer customer;
  Order order;

  @BeforeEach
  void setUp() {
    customer = customerRepository.save(new Customer(123, "u", "p", true));
    order = new Order(123, customer, new Date(), new ArrayList<>());
  }

  @AfterEach
  void tearDown() {
    customer = null;
    order = null;
  }

  @Test
  void whenSave_shouldReturnSavedOrder() {
    Order saved = orderRepository.save(order);
    assertThat(saved.getOrderDate()).isEqualTo(order.getOrderDate());
  }

  @Test
  void whenUpdate_shouldReturnUpdatedOrder() {
    Order saved = orderRepository.save(order);
    Order found = orderRepository.findById(saved.getId()).get();
    found.setCustomer(new Customer(21, "nn", "pp", false));
    Order updated = orderRepository.save(found);
    assertThat(updated).isNotNull().isEqualTo(found);
  }

  @Test
  void whenDelete_shouldRemoveOrder() {
    Order saved = orderRepository.save(order);
    orderRepository.deleteById(saved.getId());
    Optional<Order> found = orderRepository.findById(saved.getId());
    assertThat(found).isEmpty();
  }

  @Test
  void whenFindById_shouldReturnOrder() {
    Order saved = orderRepository.save(order);
    Order found = orderRepository.findById(saved.getId()).get();
    assertThat(found).isNotNull().isEqualTo(saved);
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<Order> orders = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      orders.add(new Order(i, customer, new Date(), new ArrayList<>()));
      orderRepository.save(orders.get(i));
    }
    List<Order> foundList = orderRepository.findAll();
    assertThat(foundList).hasSizeGreaterThanOrEqualTo(orders.size());
  }

  @Test
  void whenFindAllByCustomerId_shouldReturnList() {
    List<Order> orders = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      orders.add(orderRepository.save(new Order(i, customer, new Date(), new ArrayList<>())));
    }
    List<Order> foundList = orderRepository.findAllByCustomerId(customer.getId());
    assertThat(foundList).hasSameElementsAs(orders);
  }
}
