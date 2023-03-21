package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.model.Cart;
import java.util.stream.Collectors;

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
    return new Cart()
        .setId(cartDto.getId())
        .setCustomer(CustomerMapper.getInstance().toEntity(cartDto.getCustomer()))
        .setCartDetails(
            cartDto.getCartDetails().stream()
                .map(cartDetailDto -> CartDetailMapper.getInstance().toEntity(cartDetailDto))
                .collect(Collectors.toList()));
  }

  /**
   * To dto cart dto.
   *
   * @param cart the cart
   * @return the cart dto
   */
  public CartDto toDto(Cart cart) {
    return new CartDto()
        .setId(cart.getId())
        .setCustomer(CustomerMapper.getInstance().toDto(cart.getCustomer()))
        .setCartDetails(
            cart.getCartDetails().stream()
                .map(cartDetail -> CartDetailMapper.getInstance().toDto(cartDetail))
                .collect(Collectors.toList()));
  }
}
