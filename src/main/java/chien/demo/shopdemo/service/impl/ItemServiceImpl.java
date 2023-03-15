package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.ItemDTO;
import chien.demo.shopdemo.mapper.ItemMapper;
import chien.demo.shopdemo.model.Item;
import chien.demo.shopdemo.repository.ItemRepository;
import chien.demo.shopdemo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream().map(item ->
                ItemMapper.getInstance().toDTO(item)).collect(Collectors.toList());
    }

    @Override
    public ItemDTO create(ItemDTO dto) {
        Item item = ItemMapper.getInstance().toEntity(dto);
        return ItemMapper.getInstance().toDTO(item);
    }

    @Override
    public ItemDTO update(int id, ItemDTO dto) {
        Item item = itemRepository.findById(id).get();
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        return ItemMapper.getInstance().toDTO(item);
    }

    @Override
    public void delete(int id) {
        Item item = itemRepository.findById(id).get();
        itemRepository.delete(item);
    }

    @Override
    public ItemDTO findById(int id) {
        return ItemMapper.getInstance().toDTO(itemRepository.findById(id).get());
    }
}
