package chien.demo.shopdemo.TestController;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    CustomerService customerService;

    public TestController(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    @GetMapping("getallcustomers")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers().stream().map(customer -> modelMapper.map(customer,
                CustomerDto.class)).collect(Collectors.toList());
    }
}
