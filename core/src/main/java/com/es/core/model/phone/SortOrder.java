package com.es.core.model.phone;

public enum SortOrder {
    ASC("asc"), DESC("desc");

    private String value;

    SortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
