package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Order detail repository. */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {}
