package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findById(int id);
}
