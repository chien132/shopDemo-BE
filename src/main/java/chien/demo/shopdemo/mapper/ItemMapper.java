package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.ItemDTO;
import chien.demo.shopdemo.model.Item;
import chien.demo.shopdemo.model.OrderDetail;

public class ItemMapper {

    public static ItemMapper INSTANCE;

    public static ItemMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ItemMapper();
        }
        return INSTANCE;
    }

    public Item toEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        return item;
    }

    public ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
}
