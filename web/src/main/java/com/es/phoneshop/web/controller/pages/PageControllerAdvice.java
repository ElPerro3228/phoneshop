package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.JdbcPhoneDaoException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PageControllerAdvice {
    @ExceptionHandler(JdbcPhoneDaoException.class)
    public String handleJdbcPhoneDaoException() {
        return "errors/errorPage";
    }
}
