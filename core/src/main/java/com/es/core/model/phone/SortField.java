package com.es.core.model.phone;

public enum SortField {
    BRAND("brand"), MODEL("model"), DISPLAY_SIZE_INCHES("displaySizeInches"), PRICE("price");

    private String value;

    SortField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
