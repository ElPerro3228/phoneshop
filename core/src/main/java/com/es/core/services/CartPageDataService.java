package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartDTO;
import com.es.core.model.phone.Phone;

import java.util.Map;

public interface CartPageDataService {
    CartDTO createCartPageData(Cart cart);
    Map<Long, Long> convert(Map<Phone, Long> cartItems);
}
