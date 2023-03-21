package chien.demo.shopdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import chien.demo.shopdemo.model.Cart;
import chien.demo.shopdemo.model.CartDetail;
import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.model.Item;
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
class CartDetailRepositoryTest {
  @Autowired CartDetailRepository cartDetailRepository;
  @Autowired CartRepository cartRepository;
  @Autowired CustomerRepository customerRepository;
  @Autowired ItemRepository itemRepository;
  CartDetail cartDetail;
  Item item;
  Cart cart;
  Customer customer;

  @BeforeEach
  void setUp() {
    customer = customerRepository.save(new Customer(123, "u", "p", false));
    cart = cartRepository.save(new Cart(123, customer, new ArrayList<>()));
    item = itemRepository.save(new Item(123, "Test", 1));
    cartDetail = new CartDetail(123, cart, item, 1, new Date());
  }

  @AfterEach
  void tearDown() {
    customer = null;
    cartDetail = null;
    cart = null;
    item = null;
  }

  @Test
  void whenSave_shouldReturnSavedCartDetail() {
    CartDetail saved = cartDetailRepository.save(cartDetail);
    assertThat(saved).isNotNull();
    assertThat(saved.getDateAdded()).isEqualTo(cartDetail.getDateAdded());
  }

  @Test
  void whenUpdate_shouldReturnUpdatedCartDetail() {
    CartDetail saved = cartDetailRepository.save(cartDetail);
    CartDetail found = cartDetailRepository.findById(saved.getId()).get();
    found.setQuantity(99);
    CartDetail updated = cartDetailRepository.save(found);
    assertThat(updated).isNotNull().isEqualTo(found);
  }

  @Test
  void whenDelete_shouldRemoveCartDetail() {
    CartDetail saved = cartDetailRepository.save(cartDetail);
    cartDetailRepository.deleteById(saved.getId());
    Optional<CartDetail> found = cartDetailRepository.findById(saved.getId());
    assertThat(found).isEmpty();
  }

  @Test
  void whenFindById_shouldReturnCartDetail() {
    CartDetail saved = cartDetailRepository.save(cartDetail);
    CartDetail found = cartDetailRepository.findById(saved.getId()).get();
    assertThat(found).isEqualTo(saved);
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<CartDetail> cartDetails = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      cartDetails.add(new CartDetail(i, cart, item, 1, new Date()));
      cartDetailRepository.save(cartDetails.get(i));
    }
    List<CartDetail> foundList = cartDetailRepository.findAll();
    assertThat(foundList).isNotNull().hasSizeGreaterThanOrEqualTo(cartDetails.size());
  }
}
