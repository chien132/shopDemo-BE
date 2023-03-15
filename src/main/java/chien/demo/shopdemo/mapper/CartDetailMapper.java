package chien.demo.shopdemo.mapper;


import chien.demo.shopdemo.dto.CartDetailDTO;
import chien.demo.shopdemo.model.CartDetail;

public class CartDetailMapper {
    public static CartDetailMapper INSTANCE;

    public static CartDetailMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartDetailMapper();
        }
        return INSTANCE;
    }

    public CartDetail toEntity(CartDetailDTO cartDTO) {
        CartDetail cart = new CartDetail();
        cart.setId(cartDTO.getId());
        cart.setItem(ItemMapper.getInstance().toEntity(cartDTO.getItem()));
        cart.setQuantity(cartDTO.getQuantity());
        cart.setCart(CartMapper.getInstance().toEntity(cartDTO.getCart()));
        cart.setDateAdded(cartDTO.getDateAdded());
        return cart;
    }

    public CartDetailDTO toDTO(CartDetail cart) {
        CartDetailDTO cartDTO = new CartDetailDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setItem(ItemMapper.getInstance().toDTO(cart.getItem()));
        cartDTO.setQuantity(cart.getQuantity());
        cartDTO.setCart(CartMapper.getInstance().toDTO(cart.getCart()));
        cartDTO.setDateAdded(cart.getDateAdded());
        return cartDTO;
    }
}
