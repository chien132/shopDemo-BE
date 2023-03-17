package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.mapper.CartMapper;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.model.Cart;
import chien.demo.shopdemo.repository.CartRepository;
import chien.demo.shopdemo.service.CartService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Cart service. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class CartServiceImpl implements CartService {

  @Autowired private CartRepository cartRepository;

  @Override
  public List<CartDto> findAll() {
    return cartRepository.findAll().stream()
        .map(cart -> CartMapper.getInstance().toDto(cart))
        .collect(Collectors.toList());
  }

  @Override
  public CartDto create(CartDto dto) {
    Cart cart = CartMapper.getInstance().toEntity(dto);
    return CartMapper.getInstance().toDto(cartRepository.save(cart));
  }

  @Override
  public CartDto update(int id, CartDto dto) {
    Optional<Cart> result = cartRepository.findById(id);
    Cart cart = result.orElseGet(Cart::new);
    cart.setId(id);
    cart.setCustomer(CustomerMapper.getInstance().toEntity(dto.getCustomer()));
    return CartMapper.getInstance().toDto(cartRepository.save(cart));
  }

  @Override
  public void deleteById(int id) {
    Optional<Cart> result = cartRepository.findById(id);
    if (result.isPresent()) {
      cartRepository.deleteById(result.get().getId());
    }
  }

  @Override
  public CartDto findById(int id) {
    Optional<Cart> result = cartRepository.findById(id);
    if (result.isPresent()) {
      return CartMapper.getInstance().toDto(result.get());
    } else {
      return null;
    }
  }
}
