package chien.demo.shopdemo.service;

import chien.demo.shopdemo.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findAll();

    List<OrderDTO> findAllByCustomer_Id(int id);

    OrderDTO create(OrderDTO dto);

    OrderDTO update(int id, OrderDTO dto);

    void delete(int id);

    OrderDTO findById(int id);

}
