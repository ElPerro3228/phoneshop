package com.es.phoneshop.web.controller.pages;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class PageControllerAdvice {
//    @ExceptionHandler(Exception.class)
    public String handleJdbcPhoneDaoException() {
        return "errors/errorPage";
    }
}
