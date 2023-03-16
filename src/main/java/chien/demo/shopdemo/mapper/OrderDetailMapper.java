package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.OrderDetailDto;
import chien.demo.shopdemo.model.OrderDetail;

/** The type Order detail mapper. */
public class OrderDetailMapper {
  /** The constant INSTANCE. */
  private static OrderDetailMapper instance;

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static OrderDetailMapper getInstance() {
    if (instance == null) {
      instance = new OrderDetailMapper();
    }
    return instance;
  }

  /**
   * To entity order detail.
   *
   * @param orderDetailDto the order detail dto
   * @return the order detail
   */
  public OrderDetail toEntity(OrderDetailDto orderDetailDto) {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setId(orderDetailDto.getId());
    orderDetail.setOrder(OrderMapper.getInstance().toEntity(orderDetailDto.getOrder()));
    orderDetail.setItem(ItemMapper.getInstance().toEntity(orderDetailDto.getItem()));
    orderDetail.setQuantity(orderDetailDto.getQuantity());
    return orderDetail;
  }

  /**
   * To dto order detail dto.
   *
   * @param orderDetail the order detail
   * @return the order detail dto
   */
  public OrderDetailDto toDto(OrderDetail orderDetail) {
    OrderDetailDto orderDetailDto = new OrderDetailDto();
    orderDetailDto.setId(orderDetail.getId());
    orderDetailDto.setOrder(OrderMapper.getInstance().toDto(orderDetail.getOrder()));
    orderDetailDto.setItem(ItemMapper.getInstance().toDto(orderDetail.getItem()));
    orderDetailDto.setQuantity(orderDetail.getQuantity());
    return orderDetailDto;
  }
}
