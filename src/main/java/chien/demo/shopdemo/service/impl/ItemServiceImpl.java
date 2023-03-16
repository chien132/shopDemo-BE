package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.mapper.ItemMapper;
import chien.demo.shopdemo.model.Item;
import chien.demo.shopdemo.repository.ItemRepository;
import chien.demo.shopdemo.service.ItemService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Item service. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class ItemServiceImpl implements ItemService {

  @Autowired private ItemRepository itemRepository;

  @Override
  public List<ItemDto> findAll() {
    return itemRepository.findAll().stream()
        .map(item -> ItemMapper.getInstance().toDto(item))
        .collect(Collectors.toList());
  }

  @Override
  public ItemDto create(ItemDto dto) {
    Item item = ItemMapper.getInstance().toEntity(dto);
    return ItemMapper.getInstance().toDto(item);
  }

  @Override
  public ItemDto update(int id, ItemDto dto) {
    Optional<Item> result = itemRepository.findById(id);
    Item item = result.orElseGet(Item::new);
    item.setId(id);
    item.setName(dto.getName());
    item.setPrice(dto.getPrice());
    return ItemMapper.getInstance().toDto(item);
  }

  @Override
  public void delete(int id) {
    Optional<Item> result = itemRepository.findById(id);
    if (result.isPresent()) {
      itemRepository.delete(result.get());
    }
  }

  @Override
  public ItemDto findById(int id) {
    Optional<Item> result = itemRepository.findById(id);
    if (result.isPresent()) {
      return ItemMapper.getInstance().toDto(result.get());
    } else {
      return null;
    }
  }
}
