package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.CartDetailDTO;
import chien.demo.shopdemo.mapper.CartDetailMapper;
import chien.demo.shopdemo.mapper.CartMapper;
import chien.demo.shopdemo.mapper.ItemMapper;
import chien.demo.shopdemo.model.CartDetail;
import chien.demo.shopdemo.repository.CartDetailRepository;
import chien.demo.shopdemo.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<CartDetailDTO> findAll() {
        return cartDetailRepository.findAll().stream().map(cartDetail ->
                CartDetailMapper.getInstance().toDTO(cartDetail)).collect(Collectors.toList());
    }

    @Override
    public CartDetailDTO create(CartDetailDTO dto) {
        CartDetail cartDetail = CartDetailMapper.getInstance().toEntity(dto);
        return CartDetailMapper.getInstance().toDTO(cartDetailRepository.save(cartDetail));
    }

    @Override
    public CartDetailDTO update(int id, CartDetailDTO dto) {
        CartDetail cartDetail = cartDetailRepository.findById(id).get();
        cartDetail.setCart(CartMapper.getInstance().toEntity(dto.getCart()));
        cartDetail.setItem(ItemMapper.getInstance().toEntity(dto.getItem()));
        cartDetail.setQuantity(dto.getQuantity());
        cartDetail.setDateAdded(dto.getDateAdded());
        return CartDetailMapper.getInstance().toDTO(cartDetailRepository.save(cartDetail));
    }

    @Override
    public void delete(int id) {
        CartDetail cartDetail = cartDetailRepository.findById(id).get();
        cartDetailRepository.delete(cartDetail);
    }

    @Override
    public CartDetailDTO findById(int id) {
        return CartDetailMapper.getInstance().toDTO(cartDetailRepository.findById(id).get());
    }
}
