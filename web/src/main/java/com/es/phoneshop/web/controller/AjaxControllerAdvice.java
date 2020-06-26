package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemValidationException;
import com.es.core.order.OutOfStockException;
import com.es.core.validators.CartPageDataValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class AjaxControllerAdvice {

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler(CartItemValidationException.class)
    public ResponseEntity<AjaxError> handleCartItemValidationException(CartItemValidationException exception) {
        AjaxError error = new AjaxError(createMessage(exception.getErrors()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<AjaxError> handleOutOfStockException(OutOfStockException exception) {
        String message = messageSource.getMessage(exception.getErrorCode(), null, LocaleContextHolder.getLocale());
        AjaxError error = new AjaxError(message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartPageDataValidationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(CartPageDataValidationException exception) {
        Map<String, String> errorsMap = createErrorsMap(exception.getErrors());
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> createErrorsMap(Errors errors) {
        Map<String, String> errorsMap = new HashMap<>();
        for (ObjectError error : errors.getAllErrors()) {
            errorsMap.put(error.getCode(), error.getDefaultMessage());
        }
        return errorsMap;
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
