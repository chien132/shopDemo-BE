package chien.demo.shopdemo.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The type Api exception handler.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Cart detail not found exception error message.
     *
     * @param ex      the ex
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
     * @param ex      the ex
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
     * @param ex      the ex
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
     * @param ex      the ex
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
     * @param ex      the ex
     * @param request the request
     * @return the error message
     */
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage itemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        return new ErrorMessage(404, "Cannot find the specified Item with the provided Item Id");
    }

    /**
     * Quantity less than one exception error message.
     *
     * @param ex      the ex
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
     * @param ex      the ex
     * @param request the request
     * @return the error message
     */
    @ExceptionHandler(PaymentFailureException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage paymentFailureException(PaymentFailureException ex, WebRequest request) {
        return new ErrorMessage(401, "Payment failed!");
    }

    /**
     * Payment failure exception error message.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error message
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage badRequestException(Exception ex, WebRequest request) {
        return new ErrorMessage(400, ex.getMessage());
    }


    /**
     * Handle exception when the request not found data.
     *
     * @param e ResourceNotFoundException
     * @return ErrorResponse
     */
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "404 Response",
                                    summary = "Handle exception when resource not found",
                                    value = """
                                            {
                                              "statusCode": 404,
                                              "message": "{data} not found"
                                            }
                                            """
                            ))})
    })
    public ErrorMessage handleResourceNotFoundException(Exception e) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

}
