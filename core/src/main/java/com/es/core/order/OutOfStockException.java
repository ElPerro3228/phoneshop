package com.es.core.order;

public class OutOfStockException extends Exception {

    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
