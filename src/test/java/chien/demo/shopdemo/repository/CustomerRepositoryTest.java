package chien.demo.shopdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
class CustomerRepositoryTest {

  @Autowired CustomerRepository customerRepository;
  Customer customer;

  @BeforeEach
  void setUp() {
    customer = new Customer(123, "Username", "password", false);
  }

  @AfterEach
  void tearDown() {
    customer = null;
  }

  @Test
  void whenSave_shouldReturnSavedCustomer() {
    Customer savedCustomer = customerRepository.save(customer);
    assertThat(savedCustomer).isNotNull();
    assertThat(savedCustomer.getUsername()).isEqualTo(customer.getUsername());
  }

  @Test
  void whenUpdate_shouldReturnCustomer() {
    Customer savedCustomer = customerRepository.save(customer);
    Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();
    foundCustomer.setUsername("changed name");
    Customer updatedCustomer = customerRepository.save(foundCustomer);
    assertThat(updatedCustomer).isNotNull().isEqualTo(foundCustomer);
  }

  @Test
  void whenDeleteById_shouldRemoveCustomer() {
    Customer savedCustomer = customerRepository.save(customer);
    customerRepository.deleteById(savedCustomer.getId());
    Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());
    assertThat(foundCustomer).isEmpty();
  }

  @Test
  void whenFindById_shouldReturnCustomer() {
    Customer savedCustomer = customerRepository.save(customer);
    Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();
    assertThat(foundCustomer).isNotNull().isEqualTo(savedCustomer);
  }

  @Test
  void whenFindByUsername_shouldReturnCustomer() {
    Customer savedCustomer = customerRepository.save(customer);
    Customer foundCustomer = customerRepository.findByUsername(savedCustomer.getUsername());
    assertThat(foundCustomer).isNotNull().isEqualTo(savedCustomer);
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<Customer> customerLists = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      customerLists.add(new Customer(i, "username" + i, "password" + i, false));
      customerRepository.save(customerLists.get(i));
    }
    List<Customer> customers = customerRepository.findAll();
    assertThat(customers).isNotNull().hasSizeGreaterThanOrEqualTo(customerLists.size());
  }
}
