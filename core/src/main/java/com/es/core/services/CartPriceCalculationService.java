package com.es.core.services;

import com.es.core.cart.Cart;

import java.math.BigDecimal;

public interface CartPriceCalculationService {
    BigDecimal calculateCartPrice(Cart cart);
}
