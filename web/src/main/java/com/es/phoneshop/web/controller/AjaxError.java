package com.es.phoneshop.web.controller;

import java.io.Serializable;

public class AjaxError implements Serializable {
    private String message;

    public AjaxError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
