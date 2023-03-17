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
import chien.demo.shopdemo.mapper.CartDetailMapper;
import chien.demo.shopdemo.model.CartDetail;
import chien.demo.shopdemo.repository.CartDetailRepository;
import chien.demo.shopdemo.service.impl.CartDetailServiceImpl;
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
class CartDetailServiceTest {
  @Mock CartDetailRepository cartDetailRepository;
  @InjectMocks CartDetailServiceImpl cartDetailService;

  CartDetailDto cartDetailDto;
  CartDetail cartDetail;
  CartDto cartDto;
  ItemDto itemDto;

  @BeforeEach
  void setUp() {
    cartDto = new CartDto(1, new CustomerDto(1, "u", "p", true));
    itemDto = new ItemDto(1, "Item test", 215);
    cartDetailDto =
        new CartDetailDto(1, cartDto, itemDto, 12, new Date(System.currentTimeMillis()));
    cartDetail = CartDetailMapper.getInstance().toEntity(cartDetailDto);
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
      mockCartDetails.add(
          new CartDetailDto(i, cartDto, itemDto, i * 2, new Date(System.currentTimeMillis())));
    }
    given(cartDetailRepository.findAll())
        .willReturn(
            mockCartDetails.stream()
                .map(cd -> CartDetailMapper.getInstance().toEntity(cd))
                .collect(Collectors.toList()));
    List<CartDetailDto> actualCartDetails = cartDetailService.findAll();
    assertThat(actualCartDetails).hasSameElementsAs(mockCartDetails);
    verify(cartDetailRepository).findAll();
  }

  @Test
  void whenCreate_shouldReturnCartDetail() {
    given(cartDetailRepository.save(cartDetail)).willReturn(cartDetail);
    CartDetailDto savedCartDetail = cartDetailService.create(cartDetailDto);
    assertThat(savedCartDetail).isEqualTo(cartDetailDto);
    verify(cartDetailRepository, times(1)).save(cartDetail);
  }

  @Test
  void whenUpdate_shouldReturnCartDetail() {
    given(cartDetailRepository.findById(cartDetail.getId())).willReturn(Optional.of(cartDetail));
    given(cartDetailRepository.save(cartDetail)).willReturn(cartDetail);
    cartDetailDto.setQuantity(1212);
    CartDetailDto updatedCartDetail =
        cartDetailService.update(cartDetailDto.getId(), cartDetailDto);
    assertThat(updatedCartDetail).isEqualTo(cartDetailDto);
    verify(cartDetailRepository, times(1)).save(cartDetail);
  }

  @Test
  void whenDelete_shouldReturnTrue() {
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
