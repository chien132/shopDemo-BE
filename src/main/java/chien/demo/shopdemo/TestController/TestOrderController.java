package chien.demo.shopdemo.TestController;

import chien.demo.shopdemo.dto.OrderDTO;
import chien.demo.shopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders/")
public class TestOrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@PathVariable(name = "id") int id){
        return ResponseEntity.ok(orderService.findAllByCustomer_Id(id));
    }
}
