package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** The interface Order repository. */
public interface OrderRepository extends JpaRepository<Order, Integer> {
  /**
   * Find all by customer id list.
   *
   * @param id the id
   * @return the list
   */
  List<Order> findAllByCustomerId(int id);
}
