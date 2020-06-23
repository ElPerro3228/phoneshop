package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartPageDTO;

public interface CartPageDataService {
    CartPageDTO createCartPageData(Cart cart);
}
