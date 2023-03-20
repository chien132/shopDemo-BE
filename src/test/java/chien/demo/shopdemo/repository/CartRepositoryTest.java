package chien.demo.shopdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import chien.demo.shopdemo.model.Cart;
import chien.demo.shopdemo.model.Customer;
import java.util.ArrayList;
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
class CartRepositoryTest {
  @Autowired CartRepository cartRepository;
  @Autowired CustomerRepository customerRepository;

  Cart cart;
  Customer customer;

  @BeforeEach
  void setUp() {
    customer = customerRepository.save(new Customer(123, "un", "pw", true));
    cart = new Cart(123, customer);
  }

  @AfterEach
  void tearDown() {
    cart = null;
    customer = null;
  }

  @Test
  void whenSave_shouldReturnSavedCart() {
    Cart savedCart = cartRepository.save(cart);
    assertThat(savedCart.getCustomer()).isEqualTo(cart.getCustomer());
  }

  @Test
  void whenUpdate_shouldReturnCart() {
    Cart savedCart = cartRepository.save(cart);
    Cart foundCart = cartRepository.findById(savedCart.getId()).get();
    foundCart.setCustomer(new Customer(60, "newun", "newpw", true));
    Cart updatedCart = cartRepository.save(foundCart);
    assertThat(updatedCart).isNotNull().isEqualTo(foundCart);
  }

  @Test
  void whenDelete_shouldRemoveCart() {
    Cart savedCart = cartRepository.save(cart);
    cartRepository.deleteById(savedCart.getId());
    Optional<Cart> foundCart = cartRepository.findById(savedCart.getId());
    assertThat(foundCart).isEmpty();
  }

  @Test
  void whenFindById_shouldReturnCart() {
    Cart savedCart = cartRepository.save(cart);
    Cart foundCart = cartRepository.findById(savedCart.getId()).get();
    assertThat(foundCart).isNotNull().isEqualTo(savedCart);
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<Cart> carts = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      carts.add(new Cart(i, customer));
      cartRepository.save(carts.get(i));
    }
    List<Cart> foundList = cartRepository.findAll();
    assertThat(foundList).isNotNull().hasSizeGreaterThanOrEqualTo(carts.size());
  }
}
