package com.es.core.services;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartPageData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultCartPageDataService implements CartPageDataService{
    @Override
    public CartPageData createCartPageData(List<CartItem> cartItems) {
        Map<Long, Long> itemsMap = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            itemsMap.put(cartItem.getPhoneId(), cartItem.getQuantity());
        }
        return new CartPageData(itemsMap);
    }
}
