package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Cart repository. */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
  Cart findByCustomerId(int id);
}
