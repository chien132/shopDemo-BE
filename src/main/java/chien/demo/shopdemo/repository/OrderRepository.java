package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** The interface Order repository. */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  /**
   * Find all by customer id list.
   *
   * @param id the id
   * @return the list
   */
  List<Order> findAllByCustomerIdOrderByIdDesc(int id);

  @Query(
      nativeQuery = true,
      value = "select TOP 1 * FROM ORDERS where customerId =:customerId ORDER BY ID DESC")
  Optional<Order> findLatestByCustomerId(int customerId);
}
