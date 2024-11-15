package chien.demo.shopdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.dto.OrderDto;
import chien.demo.shopdemo.mapper.OrderMapper;
import chien.demo.shopdemo.model.Order;
import chien.demo.shopdemo.repository.OrderRepository;
import chien.demo.shopdemo.service.impl.OrderServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
class OrderServiceTest {

  @Mock OrderRepository orderRepository;

  @InjectMocks OrderServiceImpl orderService;

  OrderDto orderDto;
  Order order;

  @BeforeEach
  void setUp() {
    orderDto =
        new OrderDto(
            1, new CustomerDto(1, "u", "p", true), LocalDate.now(), Collections.emptyList(), false);
    order = OrderMapper.INSTANCE.toEntity(orderDto);
  }

  @AfterEach
  void tearDown() {
    order = null;
    orderDto = null;
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<OrderDto> mockOrders = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mockOrders.add(
          new OrderDto(i, new CustomerDto(), LocalDate.now(), Collections.emptyList(), false));
    }
    given(orderRepository.findAll())
        .willReturn(
            mockOrders.stream()
                .map(o -> OrderMapper.INSTANCE.toEntity(o))
                .collect(Collectors.toList()));
    List<OrderDto> actualOrders = orderService.findAll();
    assertThat(actualOrders).hasSameElementsAs(mockOrders);
    verify(orderRepository).findAll();
  }

  @Test
  void whenFindAllByCustomerId_shouldReturnList() {
    List<OrderDto> mockOrders = new ArrayList<>();
    CustomerDto customerDto = new CustomerDto(1, "Test customer", "123", true);
    for (int i = 0; i < 5; i++) {
      mockOrders.add(new OrderDto(i, customerDto, LocalDate.now(), Collections.emptyList(), false));
    }
    given(orderRepository.findAllByCustomerIdOrderByIdDesc(customerDto.getId()))
        .willReturn(
            mockOrders.stream()
                .map(o -> OrderMapper.INSTANCE.toEntity(o))
                .collect(Collectors.toList()));
    List<OrderDto> actualOrders = orderService.findAllByCustomerId(customerDto.getId());
    assertThat(actualOrders).hasSameElementsAs(mockOrders);
    verify(orderRepository).findAllByCustomerIdOrderByIdDesc(customerDto.getId());
  }

  @Test
  void whenCreate_shouldReturnOrder() {
    given(orderRepository.save(order)).willReturn(order);
    OrderDto savedOrder = orderService.create(orderDto);
    assertThat(savedOrder).isEqualTo(orderDto);
    verify(orderRepository, times(1)).save(order);
  }

  @Test
  void whenUpdate_shouldReturnOrder() {
    given(orderRepository.findById(orderDto.getId())).willReturn(Optional.of(order));
    orderDto.setCustomer(new CustomerDto(-1, "Changed username", "123", false));
    given(orderRepository.save(order)).willReturn(order);
    OrderDto updatedOrder = orderService.update(orderDto.getId(), orderDto);
    assertThat(updatedOrder).isEqualTo(orderDto);
    verify(orderRepository, times(1)).save(order);
  }

  @Test
  void whenDelete_shouldReturnTrue() {
    given(orderRepository.findById(orderDto.getId())).willReturn(Optional.of(order));
    willDoNothing().given(orderRepository).deleteById(orderDto.getId());
    orderService.deleteById(orderDto.getId());
    verify(orderRepository, times(1)).deleteById(orderDto.getId());
  }

  @Test
  void whenFindById_shouldReturnOrder() {
    given(orderRepository.findById(orderDto.getId())).willReturn(Optional.of(order));
    OrderDto foundOrder = orderService.findById(orderDto.getId());
    assertThat(foundOrder).isEqualTo(orderDto);
    verify(orderRepository, times(1)).findById(orderDto.getId());
  }
}
