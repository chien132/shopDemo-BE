package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Cart detail repository.
 */
@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {}
