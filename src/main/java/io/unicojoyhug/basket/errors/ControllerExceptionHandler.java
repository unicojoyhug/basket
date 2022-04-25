package io.unicojoyhug.basket.errors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NegativeQuantityException.class, IllegalArgumentException.class})
    public final ResponseEntity<Object> handleNegativeQuantityExceptions(Exception ex, WebRequest request) {
        String bodyOfResponse = "Quantity cannot be negative.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    public final ResponseEntity<Object> handleUnknownBasketId(Exception ex, WebRequest request) {
        String bodyOfResponse = "Unknown basket";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
