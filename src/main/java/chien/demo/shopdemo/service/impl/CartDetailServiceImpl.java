package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CartDetailDto;
import chien.demo.shopdemo.mapper.CartDetailMapper;
import chien.demo.shopdemo.mapper.CartMapper;
import chien.demo.shopdemo.mapper.ItemMapper;
import chien.demo.shopdemo.model.CartDetail;
import chien.demo.shopdemo.repository.CartDetailRepository;
import chien.demo.shopdemo.service.CartDetailService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Cart detail service. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class CartDetailServiceImpl implements CartDetailService {

  @Autowired private CartDetailRepository cartDetailRepository;

  @Override
  public List<CartDetailDto> findAll() {
    return cartDetailRepository.findAll().stream()
        .map(cartDetail -> CartDetailMapper.getInstance().toDto(cartDetail))
        .collect(Collectors.toList());
  }

  @Override
  public CartDetailDto create(CartDetailDto dto) {
    CartDetail cartDetail = CartDetailMapper.getInstance().toEntity(dto);
    return CartDetailMapper.getInstance().toDto(cartDetailRepository.save(cartDetail));
  }

  @Override
  public CartDetailDto update(int id, CartDetailDto dto) {
    Optional<CartDetail> result = cartDetailRepository.findById(id);
    CartDetail cartDetail = result.orElseGet(CartDetail::new);
    cartDetail.setId(id);
    cartDetail.setCart(CartMapper.getInstance().toEntity(dto.getCart()));
    cartDetail.setItem(ItemMapper.getInstance().toEntity(dto.getItem()));
    cartDetail.setQuantity(dto.getQuantity());
    cartDetail.setDateAdded(dto.getDateAdded());
    return CartDetailMapper.getInstance().toDto(cartDetailRepository.save(cartDetail));
  }

  @Override
  public void deleteById(int id) {
    Optional<CartDetail> result = cartDetailRepository.findById(id);
    if (result.isPresent()) {
      cartDetailRepository.deleteById(result.get().getId());
    }
  }

  @Override
  public CartDetailDto findById(int id) {
    Optional<CartDetail> result = cartDetailRepository.findById(id);
    CartDetail cartDetail;
    if (result.isPresent()) {
      cartDetail = result.get();
      return CartDetailMapper.getInstance().toDto(cartDetail);
    } else {
      return null;
    }
  }
}
