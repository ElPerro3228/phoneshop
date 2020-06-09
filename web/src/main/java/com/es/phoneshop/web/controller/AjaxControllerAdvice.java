package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemValidationException;
import com.es.core.order.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AjaxControllerAdvice {
    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<AjaxError> handleCartItemValidationException(CartItemValidationException exception) {
        AjaxError error = new AjaxError(createMessage(exception.getErrors()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<AjaxError> handleOutOfStockException(OutOfStockException exception) {
        AjaxError error = new AjaxError(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private String createMessage(Errors errors) {
        String message = "";
        for (ObjectError error : errors.getAllErrors()) {
            message += error.getDefaultMessage() + ", ";
        }
        message = message.substring(0, message.length() - 2);
        return message;
    }
}