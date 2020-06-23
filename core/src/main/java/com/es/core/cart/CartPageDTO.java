package com.es.core.cart;


import com.es.core.model.phone.Phone;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CartPageDTO implements Serializable {

    private Map<Long, Long> cartItems;
    private List<Phone> phones;
    private BigDecimal cartPrice;

    public CartPageDTO() {
    }

    public CartPageDTO(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public CartPageDTO(Map<Long, Long> cartItems, List<Phone> phones, BigDecimal cartPrice) {
        this.cartItems = cartItems;
        this.phones = phones;
        this.cartPrice = cartPrice;
    }

    public Map<Long, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public BigDecimal getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(BigDecimal cartPrice) {
        this.cartPrice = cartPrice;
    }
}
