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
    cart.setDateAdded(cartDto.getDateAdded());
    return cart;
  }

  /**
   * To dto cart detail dto.
   *
   * @param cartDetail the cart
   * @return the cart detail dto
   */
  public CartDetailDto toDto(CartDetail cartDetail) {
    CartDetailDto cartDetailDto = new CartDetailDto();
    cartDetailDto.setId(cartDetail.getId());
    cartDetailDto.setItem(ItemMapper.getInstance().toDto(cartDetail.getItem()));
    cartDetailDto.setQuantity(cartDetail.getQuantity());
    cartDetailDto.setCartId(cartDetail.getCart().getId());
    cartDetailDto.setDateAdded(cartDetail.getDateAdded());
    return cartDetailDto;
  }
}
