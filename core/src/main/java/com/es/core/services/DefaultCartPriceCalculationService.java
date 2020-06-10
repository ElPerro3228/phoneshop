package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCartPriceCalculationService implements CartPriceCalculationService{

    @Autowired
    private PhoneDao phoneDao;

    @Override
    public BigDecimal calculateCartPrice(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        BigDecimal cartPrice = new BigDecimal("0");
        for (CartItem cartItem : cartItems) {
            Optional<Phone> phone = phoneDao.get(cartItem.getPhoneId());
            BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
            if (phone.isPresent()) {
                cartPrice = cartPrice.add(phone.get().getPrice().multiply(quantity));
            }
        }
        return cartPrice;
    }
}
