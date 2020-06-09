package com.es.core.cart;


public class CartItemValidationException extends RuntimeException {

    public CartItemValidationException() {
        super();
    }

    public CartItemValidationException(String message) {
        super(message);
    }
}
