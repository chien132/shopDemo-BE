package chien.demo.shopdemo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import chien.demo.shopdemo.model.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ItemRepositoryTest {
  @Autowired ItemRepository itemRepository;
  Item item;

  @BeforeEach
  void setUp() {
    item = new Item(123, "Test item", 9999);
  }

  @AfterEach
  void tearDown() {
    item = null;
  }

  @Test
  void whenSave_shouldReturnSavedItem() {
    Item savedItem = itemRepository.save(item);
    assertThat(savedItem).isNotNull();
    assertThat(savedItem.getName()).isEqualTo(item.getName());
  }

  @Test
  void whenUpdate_shouldReturnUpdatedItem() {
    Item savedItem = itemRepository.save(item);
    Item foundItem = itemRepository.findById(savedItem.getId()).get();
    foundItem.setName("changed name");
    Item updatedItem = itemRepository.save(foundItem);
    assertThat(updatedItem).isNotNull().isEqualTo(foundItem);
  }

  @Test
  void whenDelete_shouldRemoveItem() {
    Item savedItem = itemRepository.save(item);
    itemRepository.deleteById(savedItem.getId());
    Optional<Item> foundItem = itemRepository.findById(savedItem.getId());
    assertThat(foundItem).isEmpty();
  }

  @Test
  void whenFindById_shouldReturnItem() {
    Item savedItem = itemRepository.save(item);
    Item foundItem = itemRepository.findById(savedItem.getId()).get();
    assertThat(foundItem).isNotNull().isEqualTo(savedItem);
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<Item> itemList = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      itemList.add(new Item(i, "item " + i, i * 10000));
      itemRepository.save(itemList.get(i));
    }
    List<Item> foundList = itemRepository.findAll();
    assertThat(foundList).isNotNull().hasSizeGreaterThanOrEqualTo(itemList.size());
  }
}
