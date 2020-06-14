package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class DefaultCartPriceCalculationService implements CartPriceCalculationService{

    @Value("${order.deliveryPrice}")
    private BigDecimal deliveryPrice;
    @Autowired
    private PhoneDao phoneDao;

    @Override
    public BigDecimal calculateCartPrice(Cart cart) {
        BigDecimal cartPrice = cart.getCartItems().stream()
                .map(cartItem -> phoneDao.get(cartItem.getPhoneId()).get().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return cartPrice.add(deliveryPrice);
    }
}
