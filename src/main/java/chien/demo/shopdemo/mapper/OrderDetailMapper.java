package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.OrderDetailDTO;
import chien.demo.shopdemo.model.OrderDetail;

public class OrderDetailMapper {
    public static OrderDetailMapper INSTANCE;

    public static OrderDetailMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderDetailMapper();
        }
        return INSTANCE;
    }

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailDTO.getId());
        orderDetail.setOrder(OrderMapper.getInstance().toEntity(orderDetailDTO.getOrder()));
        orderDetail.setItem(ItemMapper.getInstance().toEntity(orderDetailDTO.getItem()));
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        return orderDetail;
    }

    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setOrder(OrderMapper.getInstance().toDTO(orderDetail.getOrder()));
        orderDetailDTO.setItem(ItemMapper.getInstance().toDTO(orderDetail.getItem()));
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        return orderDetailDTO;
    }
}
