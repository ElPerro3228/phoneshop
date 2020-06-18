package com.es.core.cart;


import java.io.Serializable;
import java.util.Map;

public class CartPageData implements Serializable {

    private Map<Long, Long> cartItems;

    public CartPageData() {
    }

    public CartPageData(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<Long, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }
}
