package com.es.core.validators;

import org.springframework.validation.Errors;

public class CartPageDataValidationException extends RuntimeException{
    Errors errors;

    public CartPageDataValidationException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
