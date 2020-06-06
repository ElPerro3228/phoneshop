package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.MiniCart;

import java.math.BigDecimal;

public interface MiniCartService {
    MiniCart createMiniCart(Cart cart);
    BigDecimal countCartPrice(Cart cart);
}
