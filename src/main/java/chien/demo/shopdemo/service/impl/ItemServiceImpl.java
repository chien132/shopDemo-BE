package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.ItemCascadeDeleteError;
import chien.demo.shopdemo.exception.ItemNotFoundException;
import chien.demo.shopdemo.mapper.ItemMapper;
import chien.demo.shopdemo.model.Item;
import chien.demo.shopdemo.repository.ItemRepository;
import chien.demo.shopdemo.service.ItemService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Item service. */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Throwable.class)
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  @Override
  public List<ItemDto> findAll() {
    return ItemMapper.INSTANCE.toDtoList(itemRepository.findAll());
  }

  @Override
  public ItemDto create(ItemDto dto) {
    Item item = ItemMapper.INSTANCE.toEntity(dto);
    return ItemMapper.INSTANCE.toDto(itemRepository.save(item));
  }

  @Override
  public ItemDto update(int id, ItemDto dto) throws ItemNotFoundException {
    Optional<Item> result = itemRepository.findById(id);
    if (result.isPresent()) {
      Item item = result.get();
      item.setId(id);
      item.setName(dto.getName());
      item.setPrice(dto.getPrice());
      return ItemMapper.INSTANCE.toDto(itemRepository.save(item));
    } else {
      throw new ItemNotFoundException();
    }
  }

  @Override
  public String deleteById(int id) throws ItemCascadeDeleteError, ItemNotFoundException {
    // Item already sold
    if (itemRepository.getOrderdTime(id) > 0) {
      throw new ItemCascadeDeleteError();
    }
    Optional<Item> result = itemRepository.findById(id);
    // Item is available
    if (result.isPresent()) {
      itemRepository.deleteById(result.get().getId());
      return "deleted";
    }
    // Item is not available
    throw new ItemNotFoundException();
  }

  @Override
  public ItemDto findById(int id) {
    Optional<Item> result = itemRepository.findById(id);
    return result.map(ItemMapper.INSTANCE::toDto).orElse(null);
  }

  @Override
  public List<ItemDto> findAllByNameLike(String search) {
    return ItemMapper.INSTANCE.toDtoList(itemRepository.findAllByNameLikeIgnoreCase(search));
  }

  @Override
  public Page<ItemDto> findAllPaginated(String name, Pageable pageable) {
    return itemRepository
        .findAllByNameLikeIgnoreCase(name, pageable)
        .map(ItemMapper.INSTANCE::toDto);
  }
}
