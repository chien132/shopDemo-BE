package chien.demo.shopdemo.service.impl;

import chien.demo.shopdemo.dto.OrderDTO;
import chien.demo.shopdemo.dto.OrderDetailDTO;
import chien.demo.shopdemo.mapper.ItemMapper;
import chien.demo.shopdemo.mapper.OrderDetailMapper;
import chien.demo.shopdemo.mapper.OrderMapper;
import chien.demo.shopdemo.model.OrderDetail;
import chien.demo.shopdemo.repository.OrderDetailRepository;
import chien.demo.shopdemo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailDTO> findAll() {
        return orderDetailRepository.findAll().stream().map(orderDetail ->
                OrderDetailMapper.getInstance().toDTO(orderDetail)).collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO create(OrderDetailDTO dto) {
        OrderDetail orderDetail = OrderDetailMapper.getInstance().toEntity(dto);
        return OrderDetailMapper.getInstance().toDTO(orderDetail);
    }

    @Override
    public OrderDetailDTO update(int id, OrderDetailDTO dto) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).get();
        orderDetail.setOrder(OrderMapper.getInstance().toEntity(dto.getOrder()));
        orderDetail.setItem(ItemMapper.getInstance().toEntity(dto.getItem()));
        orderDetail.setQuantity(dto.getQuantity());
        return OrderDetailMapper.getInstance().toDTO(orderDetailRepository.save(orderDetail));
    }

    @Override
    public void delete(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).get();
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public OrderDetailDTO findById(int id) {
        return OrderDetailMapper.getInstance().toDTO(orderDetailRepository.findById(id).get());
    }
}
