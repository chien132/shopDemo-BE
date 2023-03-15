package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.CartDetailDTO;

import java.util.List;

public interface CartDetailService {
    List<CartDetailDTO> findAll();

    CartDetailDTO create(CartDetailDTO dto);

    CartDetailDTO update(int id, CartDetailDTO dto);

    void delete(int id);

    CartDetailDTO findById(int id);

}
