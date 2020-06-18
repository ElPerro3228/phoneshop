package com.es.core.validators;

import java.util.Map;

public class CartPageDataValidationException extends RuntimeException{
    Map<Long, String> errorsMap;

    public CartPageDataValidationException(Map<Long, String> errorsMap) {
        this.errorsMap = errorsMap;
    }

    public Map<Long, String> getErrorsMap() {
        return errorsMap;
    }
}
