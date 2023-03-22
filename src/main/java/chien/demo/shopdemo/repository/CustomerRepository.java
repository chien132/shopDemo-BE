package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** The interface Customer repository. */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  Optional<Customer> findByUsername(String username);

  Boolean existsByUsername(String username);
}
