package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCustomer_Id(int id);
}