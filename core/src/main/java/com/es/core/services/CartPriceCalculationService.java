package com.es.core.services;

import com.es.core.cart.Cart;

import java.math.BigDecimal;

public interface CartPriceCalculationService {
    BigDecimal calculateTotalPrice(Cart cart);
    BigDecimal calculateCartPrice(Cart cart);
    BigDecimal getDeliveryPrice();
}
