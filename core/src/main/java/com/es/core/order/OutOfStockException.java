package com.es.core.order;

public class OutOfStockException extends Exception {

    private String errorCode;

    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, String errorCode) {
        this(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
