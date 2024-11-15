package chien.demo.shopdemo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.mapper.CustomerMapper;
import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.repository.CustomerRepository;
import chien.demo.shopdemo.service.impl.CustomerServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock CustomerRepository customerRepository;

  @InjectMocks CustomerServiceImpl customerService;

  CustomerDto customerDto;
  Customer customer;

  @BeforeEach
  public void setUp() {
    customerDto = new CustomerDto(-11, "Username", "password", false);
    customer = CustomerMapper.INSTANCE.toEntity(customerDto);
  }

  @AfterEach
  public void tearDown() {
    customer = null;
    customerDto = null;
  }

  @Test
  @DisplayName("JUnit test for findAll operation")
  void whenFindAll_shouldReturnList() {
    // 1. create mock data
    List<CustomerDto> mockCustomers = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mockCustomers.add(
          new CustomerDto(i, "username " + i, "password " + i, i % 2 == 0 ? true : false));
    }
    // 2. define behavior of Repository
    given(customerRepository.findAll())
        .willReturn(
            mockCustomers.stream()
                .map(customer -> CustomerMapper.INSTANCE.toEntity(customer))
                .collect(Collectors.toList()));
    // 3. call service method
    List<CustomerDto> actualCustomers = customerService.findAll();
    // 4. assert the result
    assertThat(actualCustomers).hasSameElementsAs(mockCustomers);
    // 4.1 ensure repository is called
    verify(customerRepository).findAll();
  }

  @Test
  @DisplayName("JUnit test for create operation")
  void whenCreate_shouldReturnCustomer() {
    given(customerRepository.save(customer)).willReturn(customer);
    CustomerDto savedCustomer = customerService.create(customerDto);
    assertThat(savedCustomer).isEqualTo(customerDto);
    verify(customerRepository).save(customer);
  }

  @Test
  @DisplayName("JUnit test for update operation")
  void whenUpdate_shouldReturnCustomer() throws CustomerNotFoundException {
    given(customerRepository.findById(customerDto.getId())).willReturn(Optional.of(customer));
    customerDto.setUsername("chienne");
    given(customerRepository.save(customer)).willReturn(customer);
    CustomerDto updatedCustomer = customerService.update(customerDto.getId(), customerDto);
    assertThat(updatedCustomer).isNotNull();
    assertThat(updatedCustomer.getUsername()).isEqualTo(customerDto.getUsername());
    verify(customerRepository).save(customer);
  }

  @Test
  @DisplayName("JUnit test for delete operation")
  void whenDelete_shouldReturnTrue() throws CustomerNotFoundException {
    given(customerRepository.findById(customerDto.getId())).willReturn(Optional.of(customer));
    willDoNothing().given(customerRepository).deleteById(customerDto.getId());
    customerService.deleteById(customerDto.getId());
    verify(customerRepository, times(1)).deleteById(customerDto.getId());
  }

  @Test
  @DisplayName("JUnit test for findById operation")
  void whenFindById_shouldReturnCustomer() throws CustomerNotFoundException {
    given(customerRepository.findById(customerDto.getId())).willReturn(Optional.of(customer));
    CustomerDto foundCustomer = customerService.findById(customerDto.getId());
    assertThat(foundCustomer).isNotNull().isEqualTo(customerDto);
    verify(customerRepository).findById(customerDto.getId());
  }

  @Test
  @DisplayName("JUnit test for findById operation when not found")
  void whenGetInvalidOne_shouldThrowException() {
    int invalidCustomerId = -7;
    given(customerRepository.findById(any(Integer.class))).willReturn(Optional.ofNullable(null));
    assertThatThrownBy(() -> customerService.findById(invalidCustomerId))
        .isInstanceOf(CustomerNotFoundException.class);
    verify(customerRepository).findById(any(Integer.class));
  }
}
