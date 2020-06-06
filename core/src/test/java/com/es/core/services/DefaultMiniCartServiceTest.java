package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.MiniCart;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMiniCartServiceTest {

    @Mock
    private PhoneDao phoneDao;
    @InjectMocks
    private MiniCartService miniCartService = new DefaultMiniCartService();

    @Test
    public void shouldReturnMiniCart() {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, 2L));
        cartItems.add(new CartItem(1L, 1L));
        cart.setCartItems(cartItems);

        MiniCart miniCart = miniCartService.createMiniCart(cart);

        assertThat(miniCart.getQuantity()).isEqualTo(3);
    }

    @Test
    public void shouldCorrectlyCountCartPrice() {
        Phone mockPhone = new Phone();
        mockPhone.setPrice(new BigDecimal("1"));
        Cart testCart = new Cart();
        testCart.getCartItems().add(new CartItem(1L, 1L));
        testCart.getCartItems().add(new CartItem(2L, 2L));
        testCart.getCartItems().add(new CartItem(0L, 2L));
        when(phoneDao.get(anyLong())).thenReturn(Optional.of(mockPhone));
        when(phoneDao.get(0L)).thenReturn(Optional.empty());

        BigDecimal price = miniCartService.countCartPrice(testCart);

        assertThat(price).isEqualTo("3");
    }
}
