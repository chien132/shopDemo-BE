package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.OrderDTO;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.mapper.OrderMapper;
import chien.demo.shopdemo.model.Order;
import chien.demo.shopdemo.repository.OrderRepository;
import chien.demo.shopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(order ->
                OrderMapper.getInstance().toDTO(order)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findAllByCustomer_Id(int id) {
        return orderRepository.findAllByCustomer_Id(id).stream().map(order ->
                OrderMapper.getInstance().toDTO(order)).collect(Collectors.toList());
    }

    @Override
    public OrderDTO create(OrderDTO dto) {
        Order order = OrderMapper.getInstance().toEntity(dto);
        return OrderMapper.getInstance().toDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO update(int id, OrderDTO dto) {
        Order order = orderRepository.findById(id).get();
        order.setCustomer(CustomerMapper.getInstance().toEntity(dto.getCustomer()));
        order.setOrderDate(dto.getOrderDate());
        return OrderMapper.getInstance().toDTO(orderRepository.save(order));
    }

    @Override
    public void delete(int id) {
        Order order = orderRepository.findById(id).get();
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO findById(int id) {
        return OrderMapper.getInstance().toDTO(orderRepository.findById(id).get());
    }
}
