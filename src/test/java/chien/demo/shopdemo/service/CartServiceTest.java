package chien.demo.shopdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import chien.demo.shopdemo.dto.CartDto;
import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.mapper.CartMapper;
import chien.demo.shopdemo.model.Cart;
import chien.demo.shopdemo.repository.CartRepository;
import chien.demo.shopdemo.service.impl.CartServiceImpl;
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
class CartServiceTest {
  @Mock CartRepository cartRepository;

  @InjectMocks CartServiceImpl cartService;

  CartDto cartDto;
  Cart cart;

  @BeforeEach
  void setUp() {
    cartDto = new CartDto(1, new CustomerDto(1, "u", "p", true));
    cart = CartMapper.getInstance().toEntity(cartDto);
  }

  @AfterEach
  void tearDown() {
    cartDto = null;
    cart = null;
  }

  @Test
  void whenFindAll_shouldReturnList() {
    CustomerDto customer = new CustomerDto(1, "u", "p", true);
    List<CartDto> mockCarts = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mockCarts.add(new CartDto(i, customer));
    }
    given(cartRepository.findAll())
        .willReturn(
            mockCarts.stream()
                .map(c -> CartMapper.getInstance().toEntity(c))
                .collect(Collectors.toList()));
    List<CartDto> actualCarts = cartService.findAll();
    assertThat(actualCarts).hasSameElementsAs(mockCarts);
    verify(cartRepository, times(1)).findAll();
  }

  @Test
  void whenCreate_shouldReturnCart() {
    given(cartRepository.save(cart)).willReturn(cart);
    CartDto savedCart = cartService.create(cartDto);
    assertThat(savedCart).isEqualTo(cartDto);
    verify(cartRepository, times(1)).save(cart);
  }

  @Test
  void whenUpdate_shouldReturnCart() {
    given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));
    given(cartRepository.save(cart)).willReturn(cart);
    cartDto.setCustomer(new CustomerDto(321, "u", "p", true));
    CartDto updatedCart = cartService.update(cartDto.getId(), cartDto);
    assertThat(updatedCart).isEqualTo(cartDto);
    verify(cartRepository, times(1)).save(cart);
  }

  @Test
  void whenDelete_shouldReturnTrue() {
    given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));
    willDoNothing().given(cartRepository).deleteById(cartDto.getId());
    cartService.deleteById(cartDto.getId());
    verify(cartRepository, times(1)).deleteById(cartDto.getId());
  }

  @Test
  void whenFindById_shouldReturnCart() {
    given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));
    CartDto foundCart = cartService.findById(cartDto.getId());
    assertThat(foundCart).isEqualTo(cartDto);
    verify(cartRepository, times(1)).findById(cart.getId());
  }
}
