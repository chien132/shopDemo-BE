package chien.demo.shopdemo.controller;

import chien.demo.shopdemo.dto.CustomerDto;
import chien.demo.shopdemo.payload.request.LoginRequest;
import chien.demo.shopdemo.payload.request.SignupRequest;
import chien.demo.shopdemo.payload.response.JwtResponse;
import chien.demo.shopdemo.payload.response.MessageResponse;
import chien.demo.shopdemo.security.jwt.JwtUtils;
import chien.demo.shopdemo.security.services.UserDetailsImpl;
import chien.demo.shopdemo.service.CustomerService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Authentication controller. */
@RestController
@RequestMapping("/api")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final CustomerService customerService;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;

  /**
   * Instantiates a new Auth controller.
   *
   * @param authenticationManager the authentication manager
   * @param customerService the customer service
   * @param encoder the encoder
   * @param jwtUtils the jwt utils
   */
  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager,
      CustomerService customerService,
      PasswordEncoder encoder,
      JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.customerService = customerService;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
  }
  /**
   * Authenticate user response entity.
   *
   * @param loginRequest the login request
   * @return the response entity
   */

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userDetails.getId());
    claims.put("username", userDetails.getUsername());
    String jwt = jwtUtils.generateJwtToken(authentication, claims);
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(
        new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
  }

  /**
   * Register user response entity.
   *
   * @param signUpRequest the sign up request
   * @return the response entity
   */
  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(
      @Valid @RequestBody SignupRequest signUpRequest) {
    if (customerService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    // Create new user's account
    CustomerDto customerDto =
        new CustomerDto(
            0,
            signUpRequest.getUsername(),
            encoder.encode(signUpRequest.getPassword()),
            signUpRequest.isRole());

    customerService.create(customerDto);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
