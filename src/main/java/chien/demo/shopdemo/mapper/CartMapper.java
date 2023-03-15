package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.CartDTO;
import chien.demo.shopdemo.model.Cart;

public class CartMapper {

    public static CartMapper INSTANCE;

    public static CartMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CartMapper();
        }
        return INSTANCE;
    }

    public Cart toEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setCustomer(CustomerMapper.getInstance().toEntity(cartDTO.getCustomer()));
        return cart;
    }

    public CartDTO toDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCustomer(CustomerMapper.getInstance().toDTO(cart.getCustomer()));
        return cartDTO;
    }
}
