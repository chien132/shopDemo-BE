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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Order detail service. */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderDetailServiceImpl implements OrderDetailService {

  private final OrderDetailRepository orderDetailRepository;
  private final OrderRepository orderRepository;

  @Override
  public List<OrderDetailDto> findAll() {
    return orderDetailRepository.findAll().stream()
        .map(orderDetail -> OrderDetailMapper.INSTANCE.toDto(orderDetail))
        .collect(Collectors.toList());
  }

  @Override
  public OrderDetailDto create(OrderDetailDto dto) {
    OrderDetail orderDetail = OrderDetailMapper.INSTANCE.toEntity(dto);
    Optional<Order> order = orderRepository.findById(dto.getOrderId());
    if (order.isPresent()) {
      orderDetail.setOrder(order.get());
    }
    return OrderDetailMapper.INSTANCE.toDto(orderDetailRepository.save(orderDetail));
  }

  @Override
  public OrderDetailDto update(int id, OrderDetailDto dto) {
    //    OrderDetail orderDetail = result.orElseGet(OrderDetail::new); should check for existence
    OrderDetail orderDetail = OrderDetailMapper.INSTANCE.toEntity(dto);
    Optional<Order> order = orderRepository.findById(dto.getOrderId());
    if (order.isPresent()) {
      orderDetail.setOrder(order.get());
    }
    orderDetail.setId(id);
    return OrderDetailMapper.INSTANCE.toDto(orderDetailRepository.save(orderDetail));
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
      return OrderDetailMapper.INSTANCE.toDto(result.get());
    } else {
      return null;
    }
  }
}
