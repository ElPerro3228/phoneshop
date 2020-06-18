package com.es.core.services;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartPageData;

import java.util.List;

public interface CartPageDataService {
    CartPageData createCartPageData(List<CartItem> cartItems);
}
