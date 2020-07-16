package com.es.core.cart;


import com.es.core.model.phone.Phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CartPageDTO implements Serializable {

    private Map<Phone, Long> cartItems;
    private BigDecimal cartPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public CartPageDTO(Map<Phone, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public CartPageDTO(Map<Phone, Long> cartItems, BigDecimal cartPrice) {
        this.cartItems = cartItems;
        this.cartPrice = cartPrice;
    }

    public Map<Phone, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Phone, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(BigDecimal cartPrice) {
        this.cartPrice = cartPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartPageDTO() {
    }
}
