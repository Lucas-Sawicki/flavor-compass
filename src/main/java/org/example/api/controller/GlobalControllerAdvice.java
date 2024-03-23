package org.example.api.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import org.example.api.dto.DeliveryRangeDTO;
import org.example.domain.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@ControllerAdvice
public class GlobalControllerAdvice {
    protected Logger log;

    public GlobalControllerAdvice() {
        log = LoggerFactory.getLogger(getClass());
    }


    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ModelAndView databaseError(Exception ex) {
        String message = ex.getMessage();
        log.error("Request raised " + ex.getClass().getSimpleName());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(HttpServletRequest req, NullPointerException ex) {
        String message = "Null pointer exception occurred: [%s]. Please check if all required objects were properly initialized.".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());

        return modelAndView;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExpiredJwtException.class)
    public ModelAndView handleNullPointerException(HttpServletRequest req, ExpiredJwtException ex) {
        String message = "Token expired";
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleNullPointerException(HttpServletRequest req, ValidationException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValidException(BindingResult bindingResult, MethodArgumentNotValidException ex) {
        String message = "Please enter data in correct form: " + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingServletRequestParameterException(BindingResult bindingResult, MissingServletRequestParameterException ex) {
        String message = "Please enter data in correct form: " + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleNoResourceFound(EntityNotFoundException ex) {
        String message = String.format("Could not find a resource: [%s]", ex.getMessage());
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        modelView.addObject("errorType", ex.getClass().getSimpleName());
        return modelView;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException ex) {
        String message = "Authentication failed: " + ex.getMessage();
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException ex) {
        String message = "I/O exception occurred: [%s]. Please check if the file exists and is accessible.".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Illegal argument exception occurred: [%s]. Please check if the provided arguments are correct.".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        String message = "Access denied: " + ex.getMessage();
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView handleBadCredentialsException(HttpServletRequest req, BadCredentialsException ex) {
        String message = "Incorrect login details. Try again.";
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception ex) {
        String message = "Unexpected exception occurred: [%s]".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ModelAndView handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        String message = "Unexpected exception occurred: [%s]".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }
    @ModelAttribute("deliveryRangeDTO")
    public DeliveryRangeDTO deliveryRangeDTO() {
        return new DeliveryRangeDTO();
    }


}
