package org.edu.fabs.exchangerate.handler;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//
//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
//    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An error occurred while processing your request",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { ResponseStatusException.class })
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        String errorMessage = ex.getReason();
        HttpStatus status = (HttpStatus) ex.getStatusCode();

        return new ResponseEntity<>(errorMessage, status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        ErrorResponse response = new ErrorResponse();
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(errors.toString());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidCurrencyCodeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidCurrencyCode(InvalidCurrencyCodeException ex) {
        return new ResponseEntity<>("Invalid currency code. Currency code must be a valid ISO 4217 code.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ConversionException.class })
    public ResponseEntity<Object> handleConversionException(ConversionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
