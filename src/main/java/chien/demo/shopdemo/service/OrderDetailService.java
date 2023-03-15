package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDTO> findAll();

    OrderDetailDTO create(OrderDetailDTO dto);

    OrderDetailDTO update(int id, OrderDetailDTO dto);

    void delete(int id);

    OrderDetailDTO findById(int id);

}
