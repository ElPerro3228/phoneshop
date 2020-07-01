package com.es.core.model.order;

public enum OrderStatus {
    NEW(1L), DELIVERED(2L), REJECTED(3L);

    private Long id;

    OrderStatus() {
    }

    OrderStatus(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
