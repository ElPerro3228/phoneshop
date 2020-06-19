package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.MiniCart;
import com.es.core.order.OutOfStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMiniCartServiceTest {

    private MiniCartService miniCartService = new DefaultMiniCartService();

    @Test
    public void shouldReturnMiniCart() throws OutOfStockException {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, 2L));
        cartItems.add(new CartItem(2L, 1L));
        cart.setCartItems(cartItems);

        MiniCart miniCart = miniCartService.createMiniCart(cart);

        assertThat(miniCart.getQuantity()).isEqualTo(3);
    }


}
