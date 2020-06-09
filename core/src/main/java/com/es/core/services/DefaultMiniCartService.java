package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.MiniCart;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultMiniCartService implements MiniCartService{

    @Override
    public MiniCart createMiniCart(Cart cart) {
        MiniCart miniCart = new MiniCart();
        long quantity = countQuantity(cart);
        BigDecimal cartPrice = cart.getCartPrice();
        miniCart.setQuantity(quantity);
        miniCart.setCartPrice(cartPrice);
        return miniCart;
    }

    private long countQuantity(Cart cart) {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

}
