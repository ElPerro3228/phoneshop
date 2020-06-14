package com.es.core.cart;

import java.io.Serializable;
import java.math.BigDecimal;

public class MiniCart implements Serializable {

    private Long quantity;
    private BigDecimal cartPrice;

    public MiniCart() {
    }

    public MiniCart(Long quantity, BigDecimal cartPrice) {
        this.quantity = quantity;
        this.cartPrice = cartPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(BigDecimal cartPrice) {
        this.cartPrice = cartPrice;
    }
}
