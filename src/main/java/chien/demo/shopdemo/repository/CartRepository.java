package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/** The interface Cart repository. */
public interface CartRepository extends JpaRepository<Cart, Integer> {}
