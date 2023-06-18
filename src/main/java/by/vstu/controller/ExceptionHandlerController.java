package by.vstu.controller;

import by.vstu.dto.NamedExceptionDOO;
import by.vstu.exception.BusinessEntityNotFoundException;
import by.vstu.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    // TODO: should be updated
    @ExceptionHandler(Exception.class)
    public ResponseEntity<NamedExceptionDOO> handleInvalidFormatException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getResponseEntity("I'm teapot", "Something wrong. We are always with you", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     *   Handler for internal business exceptions
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<NamedExceptionDOO> getException(BusinessException e) {
        return new ResponseEntity<>(new NamedExceptionDOO(e.getName(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /*
     *   Handler for EntityNotFound flow exceptions
     */
    @ExceptionHandler(BusinessEntityNotFoundException.class)
    public ResponseEntity<NamedExceptionDOO> handleEntityNotFoundException(BusinessEntityNotFoundException ex) {
        return getResponseEntity(ex.getName(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /*
     *   Handler for Spring validation and custom validator exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Set<NamedExceptionDOO>> handleValidationException(MethodArgumentNotValidException ex) {
        Set<NamedExceptionDOO> exceptions = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(er -> new NamedExceptionDOO(er.getObjectName() + "." + er.getField() + ".invalid", er.getDefaultMessage()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(exceptions, HttpStatus.BAD_REQUEST);
    }

    /*
     *   Handler for DTO parse exceptions
     */
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            NumberFormatException.class
    })
    public ResponseEntity<NamedExceptionDOO> handleHttpMessageNotReadableException(Exception ex) {
        return getResponseEntity("json.parse.exception", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<NamedExceptionDOO> getResponseEntity(String code, String message, HttpStatus status) {
        return new ResponseEntity<>(new NamedExceptionDOO(code, message), status);
    }
}
