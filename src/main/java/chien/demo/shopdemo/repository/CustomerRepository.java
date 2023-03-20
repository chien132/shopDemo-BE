package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/** The interface Customer repository. */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  Customer findByUsername(String username);
}
