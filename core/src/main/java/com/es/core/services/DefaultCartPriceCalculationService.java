package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultCartPriceCalculationService implements CartPriceCalculationService{

    @Autowired
    private PhoneDao phoneDao;

    @Override
    public BigDecimal calculateCartPrice(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        double price = cartItems.stream()
                .filter(cartItem -> phoneDao.get(cartItem.getPhoneId()).isPresent())
                .mapToDouble(cartItem -> phoneDao.get(cartItem.getPhoneId()).get().getPrice().doubleValue() * cartItem.getQuantity())
                .sum();
        return new BigDecimal(price);
    }
}
