package com.es.core.cart;

import com.es.core.validators.QuantityConstraint;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;

@QuantityConstraint(
        phoneId = "phoneId",
        quantity = "quantity"
)
public class CartItem implements Serializable {

    private Long phoneId;
    @DecimalMin(value = "1")
    private Long quantity;

    public CartItem() {
    }

    public CartItem(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
