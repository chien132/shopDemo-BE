package chien.demo.shopdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Test authentication controller. */
@RestController
@RequestMapping("/api/test")
public class TestAuthController {
  @GetMapping("/all")
  public ResponseEntity<String> allAccess() {
    return ResponseEntity.ok("Public Content.");
  }

  @GetMapping("/customer")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<String> userAccess() {
    return ResponseEntity.ok("Customer Content.");
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('OWNER')")
  public ResponseEntity<String> adminAccess() {
    return ResponseEntity.ok("Admin Board.");
  }
}
