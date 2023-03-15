package chien.demo.shopdemo.TestController;

import chien.demo.shopdemo.dto.CustomerDTO;
import chien.demo.shopdemo.exception.CustomerNotFoundException;
import chien.demo.shopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TestController {

    @Autowired
    CustomerService customerService;

    @GetMapping("getallcustomers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(name = "id") int id) throws CustomerNotFoundException {
        CustomerDTO customerDTO = null;
        customerDTO = customerService.findById(id);
        return ResponseEntity.ok(customerDTO);
    }

    @PostMapping("customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerResponse = customerService.create(customerDTO);
        return new ResponseEntity<CustomerDTO>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("customer/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable int id, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        CustomerDTO customerResponse = customerService.update(id, customerDTO);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("customer/{id}")
    public ResponseEntity deleteCustomer(@PathVariable(name = "id") int id) throws CustomerNotFoundException {
        customerService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
