package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.OrderDetailDto;
import chien.demo.shopdemo.mapper.OrderDetailMapper;
import chien.demo.shopdemo.model.Order;
import chien.demo.shopdemo.model.OrderDetail;
import chien.demo.shopdemo.repository.OrderDetailRepository;
import chien.demo.shopdemo.repository.OrderRepository;
import chien.demo.shopdemo.service.OrderDetailService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Order detail service. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderDetailServiceImpl implements OrderDetailService {

  @Autowired private OrderDetailRepository orderDetailRepository;
  @Autowired private OrderRepository orderRepository;

  @Override
  public List<OrderDetailDto> findAll() {
    return orderDetailRepository.findAll().stream()
        .map(orderDetail -> OrderDetailMapper.getInstance().toDto(orderDetail))
        .collect(Collectors.toList());
  }

  @Override
  public OrderDetailDto create(OrderDetailDto dto) {
    OrderDetail orderDetail = OrderDetailMapper.getInstance().toEntity(dto);
    Optional<Order> order = orderRepository.findById(dto.getOrderId());
    if (order.isPresent()) {
      orderDetail.setOrder(order.get());
    }
    return OrderDetailMapper.getInstance().toDto(orderDetailRepository.save(orderDetail));
  }

  @Override
  public OrderDetailDto update(int id, OrderDetailDto dto) {
    //    OrderDetail orderDetail = result.orElseGet(OrderDetail::new); should check for existence
    OrderDetail orderDetail = OrderDetailMapper.getInstance().toEntity(dto);
    Optional<Order> order = orderRepository.findById(dto.getOrderId());
    if (order.isPresent()) {
      orderDetail.setOrder(order.get());
    }
    orderDetail.setId(id);
    return OrderDetailMapper.getInstance().toDto(orderDetailRepository.save(orderDetail));
  }

  @Override
  public void deleteById(int id) {
    Optional<OrderDetail> result = orderDetailRepository.findById(id);
    if (result.isPresent()) {
      orderDetailRepository.deleteById(result.get().getId());
    }
  }

  @Override
  public OrderDetailDto findById(int id) {
    Optional<OrderDetail> result = orderDetailRepository.findById(id);
    if (result.isPresent()) {
      return OrderDetailMapper.getInstance().toDto(result.get());
    } else {
      return null;
    }
  }
}
