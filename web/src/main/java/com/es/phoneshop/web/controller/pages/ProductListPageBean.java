package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Component
@RequestScope
public class ProductListPageBean {

    private List<Phone> phones;
    private int pagesNumber;
    private int currentPage;

    public ProductListPageBean() {
    }

    public ProductListPageBean(List<Phone> phones, int pagesNumber, int currentPage) {
        this.phones = phones;
        this.pagesNumber = pagesNumber;
        this.currentPage = currentPage;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
