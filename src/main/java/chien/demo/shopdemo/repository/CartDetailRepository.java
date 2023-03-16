package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Cart detail repository.
 */
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {}
