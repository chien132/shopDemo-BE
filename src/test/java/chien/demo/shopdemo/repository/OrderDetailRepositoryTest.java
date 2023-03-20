package chien.demo.shopdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.model.Item;
import chien.demo.shopdemo.model.Order;
import chien.demo.shopdemo.model.OrderDetail;
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
class OrderDetailRepositoryTest {
  @Autowired OrderDetailRepository orderDetailRepository;
  @Autowired OrderRepository orderRepository;
  @Autowired CustomerRepository customerRepository;
  @Autowired ItemRepository itemRepository;
  OrderDetail orderDetail;
  Item item;
  Order order;
  Customer customer;

  @BeforeEach
  void setUp() {
    customer = customerRepository.save(new Customer(123, "u", "p", false));
    order = orderRepository.save(new Order(123, customer, new Date()));
    item = itemRepository.save(new Item(123, "Test", 1));
    orderDetail = new OrderDetail(123, order, item, 2);
  }

  @AfterEach
  void tearDown() {
    orderDetail = null;
    item = null;
    order = null;
    customer = null;
  }

  @Test
  void whenSave_shouldReturnSavedOrderDetail() {
    OrderDetail saved = orderDetailRepository.save(orderDetail);
    assertThat(saved).isNotNull();
    assertThat(saved.getQuantity()).isEqualTo(orderDetail.getQuantity());
  }

  @Test
  void whenUpdated_shouldReturnUpdatedOrderDetail() {
    OrderDetail saved = orderDetailRepository.save(orderDetail);
    OrderDetail found = orderDetailRepository.findById(saved.getId()).get();
    found.setQuantity(60);
    OrderDetail updated = orderDetailRepository.save(found);
    assertThat(updated.getQuantity()).isEqualTo(found.getQuantity());
  }

  @Test
  void whenDelete_shouldRemoveOrderDetail() {
    OrderDetail saved = orderDetailRepository.save(orderDetail);
    orderDetailRepository.deleteById(saved.getId());
    Optional<OrderDetail> found = orderDetailRepository.findById(saved.getId());
    assertThat(found).isEmpty();
  }

  @Test
  void whenFindById_shouldReturnOrderDetail() {
    OrderDetail saved = orderDetailRepository.save(orderDetail);
    OrderDetail found = orderDetailRepository.findById(saved.getId()).get();
    assertThat(found).isEqualTo(saved);
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<OrderDetail> orderDetails = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      orderDetails.add(new OrderDetail(i, order, item, 1));
      orderDetailRepository.save(orderDetails.get(i));
    }
    List<OrderDetail> foundList = orderDetailRepository.findAll();
    assertThat(foundList).isNotNull().hasSizeGreaterThanOrEqualTo(orderDetails.size());
  }
}
