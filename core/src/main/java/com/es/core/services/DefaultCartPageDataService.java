package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartDTO;
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
    private PhoneService phoneService;
    @Autowired
    private CartPriceCalculationService cartPriceCalculationService;

    @Override
    public CartDTO createCartPageData(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        Map<Phone, Long> itemsMap = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            itemsMap.put(phoneService.getPhone(cartItem.getPhoneId()), cartItem.getQuantity());
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartItems(itemsMap);
        cartDTO.setCartPrice(cart.getCartPrice());
        cartDTO.setDeliveryPrice(cart.getDeliveryPrice());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        return cartDTO;
    }

    @Override
    public Map<Long, Long> convert(Map<Phone, Long> cartItems) {
        Map<Long, Long> map = new HashMap<>();
        for (Map.Entry<Phone, Long> entry : cartItems.entrySet()) {
            map.put(entry.getKey().getId(), entry.getValue());
        }
        return map;
    }
}
