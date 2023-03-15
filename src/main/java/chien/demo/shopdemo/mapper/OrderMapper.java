package chien.demo.shopdemo.mapper;

import chien.demo.shopdemo.dto.OrderDTO;
import chien.demo.shopdemo.model.Order;

public class OrderMapper {
    public static OrderMapper INSTANCE;

    public static OrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderMapper();
        }
        return INSTANCE;
    }

    public Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setCustomer(CustomerMapper.getInstance().toEntity(orderDTO.getCustomer()));
        return order;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setCustomer(CustomerMapper.getInstance().toDTO(order.getCustomer()));
        return orderDTO;
    }
}
