package org.example.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.DeliveryRangeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception ex) {
        String message = "Unexpected exception occurred: [%s]".formatted(ex.getMessage());
        log.error(message,ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleNullPointerException(NullPointerException ex) {
        String message = "Null pointer exception occurred: [%s]. Please check if all required objects were properly initialized.".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleIOException(IOException ex) {
        String message = "I/O exception occurred: [%s]. Please check if the file exists and is accessible.".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Illegal argument exception occurred: [%s]. Please check if the provided arguments are correct.".formatted(ex.getMessage());
        log.error(message, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        ModelAndView modelAndView = new ModelAndView("access_denied");
        modelAndView.addObject("errorMessage", "You do not have permission to perform this operation.");
        modelAndView.addObject("errorType", ex.getClass().getSimpleName());
        return modelAndView;
    }
    @ModelAttribute("deliveryRangeDTO")
    public DeliveryRangeDTO deliveryRangeDTO() {
        return new DeliveryRangeDTO();
    }
}
