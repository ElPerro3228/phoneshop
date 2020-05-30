package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AjaxControllerAdvice {
    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<AjaxError> handleCartItemValidationException(CartItemValidationException exception) {
        AjaxError error = new AjaxError(exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
