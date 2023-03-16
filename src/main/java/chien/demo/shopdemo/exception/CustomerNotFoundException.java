package chien.demo.shopdemo.exception;

/** The type Customer not found exception. */
public class CustomerNotFoundException extends Exception {
  /**
   * Instantiates a new Customer not found exception.
   *
   * @param id the id
   */
  public CustomerNotFoundException(int id) {
    super(String.valueOf(id));
  }
}
