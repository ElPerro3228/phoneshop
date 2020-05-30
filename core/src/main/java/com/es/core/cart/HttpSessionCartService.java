package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private Cart cart;
    @Autowired
    private PhoneDao phoneDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getPhoneId().equals(phoneId))
                .findFirst();
        if (optionalCartItem.isPresent()) {
            optionalCartItem.get().setQuantity(quantity);
        } else {
            cart.getCartItems().add(new CartItem(phoneId, quantity));
        }
        cart.setCartPrice(countCartPrice());
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Long phoneId : items.keySet()) {
            Long quantity = items.get(phoneId);
            addPhone(phoneId, quantity);
        }
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhoneId().equals(phoneId));
    }

    @Override
    public BigDecimal countCartPrice() {
        double subTotalPrice = 0.0;
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            Optional<Phone> phone = phoneDao.get(cartItem.getPhoneId());
            if (phone.isPresent()) {
                subTotalPrice += phone.get().getPrice().doubleValue() * cartItem.getQuantity();
            }
        }
        return new BigDecimal(subTotalPrice);
    }
}
