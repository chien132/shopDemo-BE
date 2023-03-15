package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> findAll();

    ItemDTO create(ItemDTO dto);

    ItemDTO update(int id, ItemDTO dto);

    void delete(int id);

    ItemDTO findById(int id);

}
