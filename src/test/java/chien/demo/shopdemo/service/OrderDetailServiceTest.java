package chien.demo.shopdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.dto.OrderDetailDto;
import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.mapper.OrderDetailMapper;
import chien.demo.shopdemo.model.OrderDetail;
import chien.demo.shopdemo.repository.OrderDetailRepository;
import chien.demo.shopdemo.service.impl.OrderDetailServiceImpl;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {
  @Mock OrderDetailRepository orderDetailRepository;

  @InjectMocks OrderDetailServiceImpl orderDetailService;

  OrderDetailDto orderDetailDto;

  OrderDetail orderDetail;
  OrderDto order;
  ItemDto item;

  @BeforeEach
  void setUp() {
    order =
        new OrderDto(1, new CustomerDto(1, "u", "p", true), new Date(System.currentTimeMillis()));
    item = new ItemDto(1, "Item", 123);
    orderDetailDto = new OrderDetailDto(1, order, item, 2);
    orderDetail = OrderDetailMapper.getInstance().toEntity(orderDetailDto);
  }

  @AfterEach
  void tearDown() {
    orderDetailDto = null;
    order = null;
    item = null;
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<OrderDetailDto> mockList = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      mockList.add(new OrderDetailDto(i, order, item, i));
    }
    given(orderDetailRepository.findAll())
        .willReturn(
            mockList.stream()
                .map(orderDetailDto -> OrderDetailMapper.getInstance().toEntity(orderDetailDto))
                .collect(Collectors.toList()));
    List<OrderDetailDto> actualList = orderDetailService.findAll();
    assertThat(actualList).hasSameElementsAs(mockList);
    verify(orderDetailRepository, times(1)).findAll();
  }

  @Test
  void whenCreate_shouldReturnOrderDetail() {
    given(orderDetailRepository.save(orderDetail)).willReturn(orderDetail);
    OrderDetailDto savedOrderDetail = orderDetailService.create(orderDetailDto);
    assertThat(savedOrderDetail).isEqualTo(orderDetailDto);
    verify(orderDetailRepository, times(1)).save(orderDetail);
  }

  @Test
  void whenUpdate_shouldReturnOrderDetail() {
    given(orderDetailRepository.findById(orderDetailDto.getId()))
        .willReturn(Optional.of(orderDetail));
    orderDetailDto.setQuantity(60);
    given(orderDetailRepository.save(orderDetail)).willReturn(orderDetail);
    OrderDetailDto updatedOrderDetail =
        orderDetailService.update(orderDetailDto.getId(), orderDetailDto);
    assertThat(updatedOrderDetail).isEqualTo(orderDetailDto);
    verify(orderDetailRepository, times(1)).save(orderDetail);
  }

  @Test
  void whenDelete_shouldReturnTrue() {
    given(orderDetailRepository.findById(orderDetailDto.getId()))
        .willReturn(Optional.of(orderDetail));
    willDoNothing().given(orderDetailRepository).deleteById(orderDetailDto.getId());
    orderDetailService.deleteById(orderDetailDto.getId());
    verify(orderDetailRepository, times(1)).deleteById(orderDetailDto.getId());
  }

  @Test
  void whenFindById_shouldReturnOrderDetail() {
    given(orderDetailRepository.findById(orderDetailDto.getId()))
        .willReturn(Optional.of(orderDetail));
    OrderDetailDto foundOrderDetail = orderDetailService.findById(orderDetailDto.getId());
    assertThat(foundOrderDetail).isEqualTo(orderDetailDto);
    verify(orderDetailRepository, times(1)).findById(orderDetailDto.getId());
  }
}
