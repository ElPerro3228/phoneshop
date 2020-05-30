package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    @Mock
    private PhoneDao phoneDao;
    @Mock
    private Cart cart;
    @InjectMocks
    private CartService cartService = new HttpSessionCartService();
    @Captor
    private ArgumentCaptor<BigDecimal> cartPriceCaptor;

    private Phone testPhone;

    @Before
    public void setup() {
        testPhone = new Phone();
        testPhone.setPrice(new BigDecimal("1"));
        when(phoneDao.get(anyLong())).thenReturn(Optional.of(testPhone));
    }

    @Test
    public void shouldAddPhoneToCartItemsListIfPhoneIsNotInCartAndCountCartPrice() {
        List<CartItem> cartItems = new ArrayList<>();
        when(cart.getCartItems()).thenReturn(cartItems);

        cartService.addPhone(1L, 1L);

        verify(cart).setCartPrice(cartPriceCaptor.capture());

        assertThat(cartPriceCaptor.getValue().doubleValue()).isEqualTo(1);
        assertThat(cartItems).hasSize(1)
                .contains(new CartItem(1L, 1L));
    }

    @Test
    public void shouldUpdatePhoneWhichIsAlreadyInCart() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, 1L));
        when(cart.getCartItems()).thenReturn(cartItems);

        cartService.addPhone(1L, 2L);

        assertThat(cartItems).hasSize(1)
                .contains(new CartItem(1L, 2L));
    }

    @Test
    public void shouldReturnCart() {
        assertThat(cartService.getCart()).isEqualTo(cart);
    }

    @Test
    public void shouldUpdateCartAddCountCartPrice() {
        List<CartItem> cartItems = new ArrayList<>();
        when(cart.getCartItems()).thenReturn(cartItems);
        Map<Long, Long> items = new HashMap<>();
        items.put(1L, 1L);
        items.put(2L, 1L);

        cartService.update(items);

        verify(cart, times(2)).setCartPrice(cartPriceCaptor.capture());

        assertThat(cartPriceCaptor.getValue().doubleValue()).isEqualTo(2);
        assertThat(cartItems).hasSize(2);
    }

    @Test
    public void shouldRemoveExistingCartItem() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, 1L));
        when(cart.getCartItems()).thenReturn(cartItems);

        cartService.remove(1L);

        assertThat(cartItems).hasSize(0);
    }

    @Test
    public void shouldCountCartPrice() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, 1L));
        cartItems.add(new CartItem(2L, 1L));
        cartItems.add(new CartItem(3L, 1L));

        when(cart.getCartItems()).thenReturn(cartItems);

        BigDecimal cartPrice = cartService.countCartPrice();

        assertThat(cartPrice.doubleValue()).isEqualTo(3);
    }
}
