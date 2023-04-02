package chien.demo.shopdemo.repository;

import chien.demo.shopdemo.model.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** The interface Item repository. */
public interface ItemRepository extends JpaRepository<Item, Integer> {
  @Query(
      nativeQuery = true,
      value = "select ((select count(*) FROM ITEMS as i, ORDERDETAILS as o "
          + "where o.itemid = i.id and i.id=:id)+(select count(*) FROM ITEMS as i,"
          + " CARTDETAILS as c where i.id=c.id and  i.id=:id)) as count")
  int getOrderdTime(@Param("id") int id);

  List<Item> findAllByNameLikeIgnoreCase(String search);
}
