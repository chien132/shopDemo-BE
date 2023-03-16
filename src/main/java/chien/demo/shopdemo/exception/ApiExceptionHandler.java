package chien.demo.shopdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/** The type Api exception handler. */
@RestControllerAdvice
public class ApiExceptionHandler {
  /**
   * Handle all exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage handleAllException(Exception ex, WebRequest request) {
    // quá trình kiểm soat lỗi diễn ra ở đây
    return new ErrorMessage(10000, ex.getLocalizedMessage());
  }

  /**
   * Cart detail not found exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(CartDetailNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage cartDetailNotFoundException(
      CartDetailNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        404, "Cannot find the specified CartDetail with the provided CartDetail Id.");
  }

  /**
   * Customer not found exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(CustomerNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage customerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        404,
        "Cannot find the specified Customer with the provided Customer Id: " + ex.getMessage());
  }

  /**
   * Empty cart exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(EmptyCartException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage emptyCartException(EmptyCartException ex, WebRequest request) {
    return new ErrorMessage(400, "Cannot create an Order with no Items in Cart.");
  }

  /**
   * Item cascade delete exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(ItemCascadeDeleteError.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage itemCascadeDeleteException(ItemCascadeDeleteError ex, WebRequest request) {
    return new ErrorMessage(500, "Cannot delete an Item that is related to a Cart or an Order.");
  }

  /**
   * Item not found exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(ItemNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage itemNotFoundException(ItemCascadeDeleteError ex, WebRequest request) {
    return new ErrorMessage(404, "Cannot find the specified Item with the provided Item Id");
  }

  /**
   * Quantity less than one exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(QuantityLessThanOneException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage quantityLessThanOneException(
      QuantityLessThanOneException ex, WebRequest request) {
    return new ErrorMessage(400, "Item quantity cannot less than 1 ");
  }

  /**
   * Payment failure exception error message.
   *
   * @param ex the ex
   * @param request the request
   * @return the error message
   */
  @ExceptionHandler(PaymentFailureException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage paymentFailureException(PaymentFailureException ex, WebRequest request) {
    return new ErrorMessage(401, "Payment failed!");
  }
}
