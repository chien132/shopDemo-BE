package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.CartDetailDto;
import chien.demo.shopdemo.model.CartDetail;

/** The type Cart detail mapper. */
public class CartDetailMapper {
  /** The constant INSTANCE. */
  private static CartDetailMapper instance;

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static CartDetailMapper getInstance() {
    if (instance == null) {
      instance = new CartDetailMapper();
    }
    return instance;
  }

  /**
   * To entity cart detail.
   *
   * @param cartDto the cart dto
   * @return the cart detail
   */
  public CartDetail toEntity(CartDetailDto cartDto) {
    CartDetail cart = new CartDetail();
    cart.setId(cartDto.getId());
    cart.setItem(ItemMapper.getInstance().toEntity(cartDto.getItem()));
    cart.setQuantity(cartDto.getQuantity());
    cart.setCart(CartMapper.getInstance().toEntity(cartDto.getCart()));
    cart.setDateAdded(cartDto.getDateAdded());
    return cart;
  }

  /**
   * To dto cart detail dto.
   *
   * @param cart the cart
   * @return the cart detail dto
   */
  public CartDetailDto toDto(CartDetail cart) {
    CartDetailDto cartDto = new CartDetailDto();
    cartDto.setId(cart.getId());
    cartDto.setItem(ItemMapper.getInstance().toDto(cart.getItem()));
    cartDto.setQuantity(cart.getQuantity());
    cartDto.setCart(CartMapper.getInstance().toDto(cart.getCart()));
    cartDto.setDateAdded(cart.getDateAdded());
    return cartDto;
  }
}
