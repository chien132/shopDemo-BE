package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Customer repository. */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  Optional<Customer> findByUsername(String username);

  Boolean existsByUsername(String username);
}
