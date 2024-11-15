package chien.demo.shopdemo.security.services;

import chien.demo.shopdemo.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** User details. */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Integer id;

  private String username;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  /**
   * Build user details.
   *
   * @param customer the customer
   * @return the user details
   */
  public static UserDetailsImpl build(Customer customer) {
    List<String> roles = new ArrayList<>();
    roles.add(customer.isType() ? "ROLE_CUSTOMER" : "ROLE_OWNER");
    List<GrantedAuthority> authorities =
        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return new UserDetailsImpl(
        customer.getId(), customer.getUsername(), customer.getPassword(), authorities);
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
