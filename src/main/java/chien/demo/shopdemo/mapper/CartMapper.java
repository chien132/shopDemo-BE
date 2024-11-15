package chien.demo.shopdemo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.model.Cart;

/** The type Cart mapper. */
@SuppressWarnings("checkstyle:InterfaceIsType")
@Mapper(componentModel = "spring")
public interface CartMapper extends AbstractMapper<Cart, CartDto> {

  /** The constant INSTANCE. */
  CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);
  //private static CartMapper instance;
  //
  ///**
  // * Gets instance.
  // *
  // * @return the instance
  // */
  //public static CartMapper getInstance() {
  //  if (instance == null) {
  //    instance = new CartMapper();
  //  }
  //  return instance;
  //}
  //
  ///**
  // * To entity cart.
  // *
  // * @param cartDto the cart dto
  // * @return the cart
  // */
  //public Cart toEntity(CartDto cartDto) {
  //  return new Cart()
  //      .setId(cartDto.getId())
  //      .setCustomer(CustomerMapper.getInstance().toEntity(cartDto.getCustomer()))
  //      .setCartDetails(
  //          cartDto.getCartDetails() == null
  //              ? Collections.emptyList()
  //              : cartDto.getCartDetails().stream()
  //                  .map(cartDetailDto -> CartDetailMapper.getInstance().toEntity(cartDetailDto))
  //                  .collect(Collectors.toList()));
  //}
  //
  ///**
  // * To dto cart dto.
  // *
  // * @param cart the cart
  // * @return the cart dto
  // */
  //public CartDto toDto(Cart cart) {
  //  return new CartDto()
  //      .setId(cart.getId())
  //      .setCustomer(CustomerMapper.getInstance().toDto(cart.getCustomer()))
  //      .setCartDetails(
  //          cart.getCartDetails() == null
  //              ? Collections.emptyList()
  //              : cart.getCartDetails().stream()
  //                  .map(cartDetail -> CartDetailMapper.getInstance().toDto(cartDetail))
  //                  .collect(Collectors.toList()));
  //}
}
