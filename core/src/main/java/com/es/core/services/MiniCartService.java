package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.MiniCart;

public interface MiniCartService {
    MiniCart createMiniCart(Cart cart);
}
