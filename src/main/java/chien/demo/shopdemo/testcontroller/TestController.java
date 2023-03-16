package chien.demo.shopdemo.testcontroller;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Test controller. */
@RestController
@RequestMapping("/api/")
public class TestController {

  /** The Customer service. */
  @Autowired CustomerService customerService;

  /**
   * Gets all customers.
   *
   * @return the all customers
   */
  @GetMapping("getallcustomers")
  public ResponseEntity<List<CustomerDto>> getAllCustomers() {
    return ResponseEntity.ok(customerService.findAll());
  }

  /**
   * Gets customer by id.
   *
   * @param id the id
   * @return the customer by id
   * @throws CustomerNotFoundException the customer not found exception
   */
  @GetMapping("customer/{id}")
  public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") int id)
      throws CustomerNotFoundException {
    CustomerDto customerDto = null;
    customerDto = customerService.findById(id);
    return ResponseEntity.ok(customerDto);
  }

  /**
   * Create customer response entity.
   *
   * @param customerDto the customer dto
   * @return the response entity
   */
  @PostMapping("customer")
  public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
    CustomerDto customerResponse = customerService.create(customerDto);
    return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
  }

  /**
   * Update response entity.
   *
   * @param id the id
   * @param customerDto the customer dto
   * @return the response entity
   * @throws CustomerNotFoundException the customer not found exception
   */
  @PutMapping("customer/{id}")
  public ResponseEntity<CustomerDto> update(
      @PathVariable int id, @RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
    CustomerDto customerResponse = customerService.update(id, customerDto);
    return ResponseEntity.ok(customerResponse);
  }

  /**
   * Delete customer response entity.
   *
   * @param id the id
   * @return the response entity
   * @throws CustomerNotFoundException the customer not found exception
   */
  @DeleteMapping("customer/{id}")
  public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable(name = "id") int id)
      throws CustomerNotFoundException {
    customerService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
