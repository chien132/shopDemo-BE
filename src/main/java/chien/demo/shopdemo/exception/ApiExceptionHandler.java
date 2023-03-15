package chien.demo.shopdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ErrorMessage(10000, ex.getLocalizedMessage());
    }

    @ExceptionHandler(CartDetailNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage cartDetailNotFoundException(CartDetailNotFoundException ex, WebRequest request) {
        return new ErrorMessage(404,
                "Cannot find the specified CartDetail with the provided CartDetail Id.");
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage customerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        return new ErrorMessage(404,
                "Cannot find the specified Customer with the provided Customer Id: " + ex.getMessage());
    }

    @ExceptionHandler(EmptyCartException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage emptyCartException(EmptyCartException ex, WebRequest request) {
        return new ErrorMessage(400, "Cannot create an Order with no Items in Cart.");
    }

    @ExceptionHandler(ItemCascadeDeleteError.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage itemCascadeDeleteException(ItemCascadeDeleteError ex, WebRequest request) {
        return new ErrorMessage(500, "Cannot delete an Item that is related to a Cart or an Order.");
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage itemNotFoundException(ItemCascadeDeleteError ex, WebRequest request) {
        return new ErrorMessage(404, "Cannot find the specified Item with the provided Item Id");
    }

    @ExceptionHandler(QuantityLessThanOneException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage quantityLessThanOneException(QuantityLessThanOneException ex, WebRequest request) {
        return new ErrorMessage(400, "Item quantity cannot less than 1 ");
    }

    @ExceptionHandler(PaymentFailureException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage paymentFailureException(PaymentFailureException ex, WebRequest request) {
        return new ErrorMessage(401, "Payment failed!");
    }

}