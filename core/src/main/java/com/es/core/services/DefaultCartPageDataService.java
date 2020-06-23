package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartPageDTO;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultCartPageDataService implements CartPageDataService{

    @Autowired
    private CartService cartService;

    @Override
    public CartPageDTO createCartPageData(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        Map<Long, Long> itemsMap = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            itemsMap.put(cartItem.getPhoneId(), cartItem.getQuantity());
        }
        List<Phone> phones = cartService.getPhones(cart);
        BigDecimal cartPrice = cart.getCartPrice();
        return new CartPageDTO(itemsMap, phones, cartPrice);
    }
}
