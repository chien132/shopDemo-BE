package chien.demo.shopdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import chien.demo.shopdemo.dto.CartDetailDto;
import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.CartDetailNotFoundException;
import chien.demo.shopdemo.mapper.CartDetailMapper;
import chien.demo.shopdemo.mapper.CartMapper;
import chien.demo.shopdemo.model.CartDetail;
import chien.demo.shopdemo.repository.CartDetailRepository;
import chien.demo.shopdemo.repository.CartRepository;
import chien.demo.shopdemo.service.impl.CartDetailServiceImpl;
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
class CartDetailServiceTest {
  @Mock CartDetailRepository cartDetailRepository;
  @Mock CartRepository cartRepository;
  @InjectMocks CartDetailServiceImpl cartDetailService;

  CartDetailDto cartDetailDto;
  CartDetail cartDetail;
  CartDto cartDto;
  ItemDto itemDto;

  @BeforeEach
  void setUp() {
    cartDto = new CartDto(1, new CustomerDto(1, "u", "p", true), Collections.emptyList());
    itemDto = new ItemDto(1, "Item test", 215);
    cartDetailDto = new CartDetailDto(1, cartDto.getId(), itemDto, 12, LocalDate.now());
    cartDetail = CartDetailMapper.getInstance().toEntity(cartDetailDto);
    cartDetail.setCart(CartMapper.getInstance().toEntity(cartDto));
  }

  @AfterEach
  void tearDown() {
    cartDetail = null;
    cartDetailDto = null;
    itemDto = null;
    cartDto = null;
  }

  @Test
  void whenFindAll_shouldReturnList() {
    List<CartDetailDto> mockCartDetails = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mockCartDetails.add(new CartDetailDto(i, cartDto.getId(), itemDto, i * 2, LocalDate.now()));
    }
    List<CartDetail> expectCartDetails =
        mockCartDetails.stream()
            .map(cd -> CartDetailMapper.getInstance().toEntity(cd))
            .collect(Collectors.toList());
    for (CartDetail cartDetail1 : expectCartDetails) {
      cartDetail1.setCart(cartDetail.getCart());
    }
    given(cartDetailRepository.findAll()).willReturn(expectCartDetails);
    List<CartDetailDto> actualCartDetails = cartDetailService.findAll();
    assertThat(actualCartDetails).hasSameElementsAs(mockCartDetails);
    verify(cartDetailRepository).findAll();
  }

  @Test
  void whenCreate_shouldReturnCartDetail() {
    given(cartDetailRepository.save(cartDetail)).willReturn(cartDetail);
    given(cartRepository.findById(cartDetail.getCart().getId()))
        .willReturn(Optional.of(cartDetail.getCart()));
    CartDetailDto savedCartDetail = cartDetailService.create(cartDetailDto);
    assertThat(savedCartDetail).isEqualTo(cartDetailDto);
    verify(cartDetailRepository, times(1)).save(cartDetail);
  }

  @Test
  void whenUpdate_shouldReturnCartDetail() throws CartDetailNotFoundException {
    //    given(cartRepository.findById(cartDetail.getCart().getId()))
    //        .willReturn(Optional.of(cartDetail.getCart()));
    given(cartDetailRepository.findById(cartDetail.getId())).willReturn(Optional.of(cartDetail));
    given(cartDetailRepository.save(cartDetail)).willReturn(cartDetail);
    cartDetailDto.setQuantity(1212);
    cartDetail.setQuantity(cartDetailDto.getQuantity());
    CartDetailDto updatedCartDetail =
        cartDetailService.update(cartDetailDto.getId(), cartDetailDto.getQuantity());
    assertThat(updatedCartDetail).isEqualTo(cartDetailDto);
    verify(cartDetailRepository, times(1)).save(cartDetail);
  }

  @Test
  void whenDelete_shouldReturnTrue() throws CartDetailNotFoundException {
    given(cartDetailRepository.findById(cartDetail.getId())).willReturn(Optional.of(cartDetail));
    willDoNothing().given(cartDetailRepository).deleteById(cartDetailDto.getId());
    cartDetailService.deleteById(cartDetailDto.getId());
    verify(cartDetailRepository, times(1)).deleteById(cartDetailDto.getId());
  }

  @Test
  void whenFindById_shouldReturnCartDetail() {
    given(cartDetailRepository.findById(cartDetail.getId())).willReturn(Optional.of(cartDetail));
    CartDetailDto foundCartDetail = cartDetailService.findById(cartDetailDto.getId());
    assertThat(foundCartDetail).isNotNull().isEqualTo(cartDetailDto);
    verify(cartDetailRepository).findById(cartDetailDto.getId());
  }
}
