package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.MiniCart;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultMiniCartService implements MiniCartService{

    private PhoneDao phoneDao;

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public MiniCart createMiniCart(Cart cart) {
        long quantity = countQuantity(cart);
        BigDecimal cartPrice = cart.getCartPrice();
        return new MiniCart(quantity, cartPrice);
    }

    @Override
    public BigDecimal countCartPrice(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        double price = cartItems.stream()
                .filter(cartItem -> phoneDao.get(cartItem.getPhoneId()).isPresent())
                .mapToDouble(cartItem -> phoneDao.get(cartItem.getPhoneId()).get().getPrice().doubleValue() * cartItem.getQuantity())
                .sum();
        return new BigDecimal(price);
    }

    private long countQuantity(Cart cart) {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }
}
