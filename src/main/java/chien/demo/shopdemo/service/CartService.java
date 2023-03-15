package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> findAll();

    CartDTO create(CartDTO dto);

    CartDTO update(int id, CartDTO dto);

    void delete(int id);

    CartDTO findById(int id);

}
