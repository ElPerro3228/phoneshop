package com.es.core.cart;


import org.springframework.validation.Errors;

public class CartItemValidationException extends RuntimeException {

    private Errors errors;

    public CartItemValidationException(Errors errors) {
        super();
        this.errors = errors;
    }

    public CartItemValidationException() {
        super();
    }

    public CartItemValidationException(String message) {
        super(message);
    }

    public Errors getErrors() {
        return errors;
    }
}
