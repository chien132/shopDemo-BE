package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CartDTO;
import chien.demo.shopdemo.mapper.CartMapper;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.model.Cart;
import chien.demo.shopdemo.repository.CartRepository;
import chien.demo.shopdemo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Throwable.class)
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartDTO> findAll() {
        return cartRepository.findAll().stream().map(cart ->
                CartMapper.getInstance().toDTO(cart)).collect(Collectors.toList());
    }

    @Override
    public CartDTO create(CartDTO dto) {
        Cart cart = CartMapper.getInstance().toEntity(dto);
        return CartMapper.getInstance().toDTO(cart);
    }

    @Override
    public CartDTO update(int id, CartDTO dto) {
        Cart cart = cartRepository.findById(id).get();
        cart.setCustomer(CustomerMapper.getInstance().toEntity(dto.getCustomer()));
        return CartMapper.getInstance().toDTO(cart);
    }

    @Override
    public void delete(int id) {
        Cart cart = cartRepository.findById(id).get();
        cartRepository.delete(cart);
    }

    @Override
    public CartDTO findById(int id) {
        return CartMapper.getInstance().toDTO(cartRepository.findById(id).get());
    }
}
