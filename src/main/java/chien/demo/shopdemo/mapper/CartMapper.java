package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.model.Cart;

/** The type Cart mapper. */
public class CartMapper {

  /** The constant INSTANCE. */
  private static CartMapper instance;

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static CartMapper getInstance() {
    if (instance == null) {
      instance = new CartMapper();
    }
    return instance;
  }

  /**
   * To entity cart.
   *
   * @param cartDto the cart dto
   * @return the cart
   */
  public Cart toEntity(CartDto cartDto) {
    Cart cart = new Cart();
    cart.setId(cartDto.getId());
    cart.setCustomer(CustomerMapper.getInstance().toEntity(cartDto.getCustomer()));
    return cart;
  }

  /**
   * To dto cart dto.
   *
   * @param cart the cart
   * @return the cart dto
   */
  public CartDto toDto(Cart cart) {
    CartDto cartDto = new CartDto();
    cartDto.setId(cart.getId());
    cartDto.setCustomer(CustomerMapper.getInstance().toDto(cart.getCustomer()));
    return cartDto;
  }
}
