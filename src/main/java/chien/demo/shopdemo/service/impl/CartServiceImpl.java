package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.mapper.CartMapper;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.model.Cart;
import chien.demo.shopdemo.repository.CartRepository;
import chien.demo.shopdemo.service.CartService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Cart service. */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;

  @Override
  public List<CartDto> findAll() {
    return CartMapper.INSTANCE.toDtoList(cartRepository.findAll());
  }

  @Override
  public CartDto create(CartDto dto) {
    Cart cart = CartMapper.INSTANCE.toEntity(dto);
    return CartMapper.INSTANCE.toDto(cartRepository.save(cart));
  }

  @Override
  public CartDto update(int id, CartDto dto) {
    Optional<Cart> result = cartRepository.findById(id);
    Cart cart = result.orElseGet(Cart::new);
    cart.setId(id);
    cart.setCustomer(CustomerMapper.INSTANCE.toEntity(dto.getCustomer()));
    return CartMapper.INSTANCE.toDto(cartRepository.save(cart));
  }

  @Override
  public void deleteById(int id) {
    Optional<Cart> result = cartRepository.findById(id);
    result.ifPresent(cart -> cartRepository.deleteById(cart.getId()));
  }

  @Override
  public CartDto findById(int id) {
    Optional<Cart> result = cartRepository.findById(id);
    return result.map(CartMapper.INSTANCE::toDto).orElse(null);
  }

  @Override
  public CartDto findByCustomerId(int id) {
    Cart foundCart = cartRepository.findByCustomerId(id);
    if (foundCart != null) {
      return CartMapper.INSTANCE.toDto(foundCart);
    } else {
      return null;
    }
  }
}
