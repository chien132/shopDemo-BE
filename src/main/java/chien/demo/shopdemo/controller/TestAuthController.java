package chien.demo.shopdemo.controller;

import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Test authentication controller. */
@RestController
@RequestMapping("/api/test")
public class TestAuthController {
  @Autowired ItemService itemService;

  @GetMapping("/all")
  public ResponseEntity<List<ItemDto>> allAccess() {
    return ResponseEntity.ok(itemService.findAll());
  }

  @GetMapping("/customer")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<List<ItemDto>> userAccess() {
    return ResponseEntity.ok(itemService.findAll());
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('OWNER')")
  public ResponseEntity<List<ItemDto>> adminAccess() {
    return ResponseEntity.ok(itemService.findAll());
  }
}
