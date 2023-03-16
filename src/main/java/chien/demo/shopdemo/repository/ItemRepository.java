package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/** The interface Item repository. */
public interface ItemRepository extends JpaRepository<Item, Integer> {}
