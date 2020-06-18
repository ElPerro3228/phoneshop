package com.es.core.cart;

import com.es.core.validators.CorrectQuantities;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CorrectQuantities
public class CartPageData implements Serializable {

    @Size(min = 1)
    private List<CartItem> cartItems;
    private Map<Long, String> errorsMap;

    public CartPageData() {
        errorsMap = new HashMap<>();
    }

    public CartPageData(List<CartItem> cartItems, Map<Long, String> errorsMap) {
        this.cartItems = cartItems;
        this.errorsMap = errorsMap;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<Long, String> getErrorsMap() {
        return errorsMap;
    }

    public void setErrorsMap(Map<Long, String> errorsMap) {
        this.errorsMap = errorsMap;
    }
}
