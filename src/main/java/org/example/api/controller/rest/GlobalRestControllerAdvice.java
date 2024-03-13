package org.example.api.controller.rest;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.exception.CustomException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<String> databaseError(Exception ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        String message = "Null pointer exception occurred: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        String message = "Token expired";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        String message = String.format("Could not find a resource: [%s]", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        String message = "Authentication failed: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        String message = "I/O exception occurred: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Illegal argument exception occurred: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "Access denied: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        String message = "Incorrect login details. Try again.";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        String message = "Unexpected exception occurred: " + ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
