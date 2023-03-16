package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.model.Order;

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
    Order order = new Order();
    order.setId(orderDto.getId());
    order.setOrderDate(orderDto.getOrderDate());
    order.setCustomer(CustomerMapper.getInstance().toEntity(orderDto.getCustomer()));
    return order;
  }

  /**
   * To dto order dto.
   *
   * @param order the order
   * @return the order dto
   */
  public OrderDto toDto(Order order) {
    OrderDto orderDto = new OrderDto();
    orderDto.setId(order.getId());
    orderDto.setOrderDate(order.getOrderDate());
    orderDto.setCustomer(CustomerMapper.getInstance().toDto(order.getCustomer()));
    return orderDto;
  }
}
