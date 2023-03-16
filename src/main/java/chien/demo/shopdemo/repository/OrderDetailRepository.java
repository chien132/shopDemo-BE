package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/** The interface Order detail repository. */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {}
