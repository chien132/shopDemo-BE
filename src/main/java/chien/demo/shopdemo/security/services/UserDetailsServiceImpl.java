package chien.demo.shopdemo.security.services;

import chien.demo.shopdemo.model.Customer;
import chien.demo.shopdemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** User details service. */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired CustomerRepository customerRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer customer =
        customerRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(customer);
  }
}
