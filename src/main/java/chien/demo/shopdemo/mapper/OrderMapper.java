package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.model.Order;
import java.util.Collections;
import java.util.stream.Collectors;

/** The type Order mapper. */
public class OrderMapper {
  /** The constant INSTANCE. */
  private static OrderMapper instance;

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static OrderMapper getInstance() {
    if (instance == null) {
      instance = new OrderMapper();
    }
    return instance;
  }

  /**
   * To entity order.
   *
   * @param orderDto the order dto
   * @return the order
   */
  public Order toEntity(OrderDto orderDto) {
    return new Order()
        .setId(orderDto.getId())
        .setOrderDate(orderDto.getOrderDate())
        .setCustomer(CustomerMapper.getInstance().toEntity(orderDto.getCustomer()))
        .setCompleted(orderDto.isCompleted())
        .setOrderDetails(
            orderDto.getOrderDetails() == null
                ? Collections.emptyList()
                : orderDto.getOrderDetails().stream()
                    .map(orderDetailDto -> OrderDetailMapper.getInstance().toEntity(orderDetailDto))
                    .collect(Collectors.toList()));
  }

  /**
   * To dto order dto.
   *
   * @param order the order
   * @return the order dto
   */
  public OrderDto toDto(Order order) {
    return new OrderDto()
        .setId(order.getId())
        .setOrderDate(order.getOrderDate())
        .setCustomer(CustomerMapper.getInstance().toDto(order.getCustomer()))
        .setCompleted(order.isCompleted())
        .setOrderDetails(
            order.getOrderDetails() == null
                ? Collections.emptyList()
                : order.getOrderDetails().stream()
                    .map(orderDetail -> OrderDetailMapper.getInstance().toDto(orderDetail))
                    .collect(Collectors.toList()));
  }
}
