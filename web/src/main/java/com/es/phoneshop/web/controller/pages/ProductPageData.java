package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;

import java.util.List;

public class ProductPageData {

    private List<Phone> phones;
    private int pagesNumber;
    private int currentPage;
    private List<String> sortFields;

    public ProductPageData() {
    }

    public ProductPageData(List<Phone> phones, int pagesNumber, int currentPage) {
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

    public List<String> getSortFields() {
        return sortFields;
    }

    public void setSortFields(List<String> sortFields) {
        this.sortFields = sortFields;
    }
}
