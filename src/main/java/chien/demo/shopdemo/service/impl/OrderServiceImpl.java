package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.mapper.OrderMapper;
import chien.demo.shopdemo.model.Order;
import chien.demo.shopdemo.repository.OrderRepository;
import chien.demo.shopdemo.service.OrderService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Order service. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderServiceImpl implements OrderService {

  @Autowired private OrderRepository orderRepository;

  @Override
  public List<OrderDto> findAll() {
    return orderRepository.findAll().stream()
        .map(order -> OrderMapper.getInstance().toDto(order))
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderDto> findAllByCustomerId(int id) {
    return orderRepository.findAllByCustomerId(id).stream()
        .map(order -> OrderMapper.getInstance().toDto(order))
        .collect(Collectors.toList());
  }

  @Override
  public OrderDto create(OrderDto dto) {
    Order order = OrderMapper.getInstance().toEntity(dto);
    return OrderMapper.getInstance().toDto(orderRepository.save(order));
  }

  @Override
  public OrderDto update(int id, OrderDto dto) {
    Optional<Order> result = orderRepository.findById(id);
    Order order = result.orElseGet(Order::new);
    order.setId(id);
    order.setCustomer(CustomerMapper.getInstance().toEntity(dto.getCustomer()));
    order.setOrderDate(dto.getOrderDate());
    order.setCompleted(dto.isCompleted());
    return OrderMapper.getInstance().toDto(orderRepository.save(order));
  }

  @Override
  public void deleteById(int id) {
    Optional<Order> result = orderRepository.findById(id);
    if (result.isPresent()) {
      orderRepository.deleteById(result.get().getId());
    }
  }

  @Override
  public OrderDto findById(int id) {
    Optional<Order> result = orderRepository.findById(id);
    if (result.isPresent()) {
      return OrderMapper.getInstance().toDto(result.get());
    } else {
      return null;
    }
  }

  @Override
  public OrderDto findLatestByCustomerId(int customerId) {
    Optional<Order> result = orderRepository.findLatestByCustomerId(customerId);
    if (result.isPresent()) {
      return OrderMapper.getInstance().toDto(result.get());
    } else {
      return null;
    }
  }
}
