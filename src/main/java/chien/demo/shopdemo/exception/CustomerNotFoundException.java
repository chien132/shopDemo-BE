package chien.demo.shopdemo.exception;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(int id) {
        super(String.valueOf(id));
    }
}
