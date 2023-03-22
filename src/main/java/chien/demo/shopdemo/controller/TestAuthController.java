package chien.demo.shopdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test authentication controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestAuthController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/customer")
  @PreAuthorize("hasRole('CUSTOMER')")
  public String userAccess() {
    return "Customer Content.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('OWNER')")
  public String adminAccess() {
    return "Admin Board.";
  }
}