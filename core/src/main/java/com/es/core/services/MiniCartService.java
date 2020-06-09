package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.MiniCart;

import java.math.BigDecimal;

public interface MiniCartService {
    MiniCart updateCartAndReturnMiniCart(CartItem cartItem);
    BigDecimal countCartPrice(Cart cart);
}
