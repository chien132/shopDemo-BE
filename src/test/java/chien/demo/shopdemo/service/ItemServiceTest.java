package chien.demo.shopdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.ItemCascadeDeleteError;
import chien.demo.shopdemo.exception.ItemNotFoundException;
import chien.demo.shopdemo.mapper.ItemMapper;
import chien.demo.shopdemo.model.Item;
import chien.demo.shopdemo.repository.ItemRepository;
import chien.demo.shopdemo.service.impl.ItemServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

  @Mock ItemRepository itemRepository;
  @InjectMocks ItemServiceImpl itemService;
  ItemDto itemDto;

  Item item;

  @BeforeEach
  void setUp() {
    itemDto = new ItemDto(1, "Test", 123);
    item = ItemMapper.INSTANCE.toEntity(itemDto);
  }

  @AfterEach
  void tearDown() {
    item = null;
    itemDto = null;
  }

  @Test
  void whenfindAll_shouldReturnList() {
    List<ItemDto> mockItems = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mockItems.add(new ItemDto(i, "Item " + i, i * 1000));
    }
    given(itemRepository.findAll())
        .willReturn(
            mockItems.stream()
                .map(itemDto -> ItemMapper.INSTANCE.toEntity(itemDto))
                .collect(Collectors.toList()));
    List<ItemDto> actualItems = itemService.findAll();
    assertThat(mockItems).hasSameElementsAs(actualItems);
    verify(itemRepository).findAll();
  }

  @Test
  void whenCreate_shouldReturnItem() {
    given(itemRepository.save(item)).willReturn(item);
    ItemDto savedItem = itemService.create(itemDto);
    assertThat(savedItem).isEqualTo(itemDto);
    verify(itemRepository).save(item);
  }

  @Test
  void whenUpdate_shouldReturnItem() throws ItemNotFoundException {
    given(itemRepository.findById(itemDto.getId())).willReturn(Optional.of(item));
    itemDto.setName("Orther name");
    given(itemRepository.save(item)).willReturn(item);
    ItemDto savedItem = itemService.update(itemDto.getId(), itemDto);
    assertThat(savedItem).isEqualTo(itemDto);
    verify(itemRepository).save(item);
  }

  @Test
  void whenDelete_shouldReturnTrue() throws ItemCascadeDeleteError, ItemNotFoundException {
    given(itemRepository.findById(itemDto.getId())).willReturn(Optional.of(item));
    willDoNothing().given(itemRepository).deleteById(itemDto.getId());
    itemService.deleteById(itemDto.getId());
    verify(itemRepository, times(1)).deleteById(itemDto.getId());
  }

  @Test
  void whenFindById_shouldReturnItem() {
    given(itemRepository.findById(itemDto.getId())).willReturn(Optional.of(item));
    ItemDto foundItem = itemService.findById(itemDto.getId());
    assertThat(foundItem).isEqualTo(itemDto);
    verify(itemRepository).findById(itemDto.getId());
  }
}
