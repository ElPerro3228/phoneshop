package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.order.OutOfStockException;
import com.es.core.services.CartPriceCalculationService;
import com.es.core.validators.QuantityValidator;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    @Mock
    private Cart cart;
    @Mock
    private CartPriceCalculationService cartPriceCalculationService;
    @Mock
    private QuantityValidator quantityValidator;
    @InjectMocks
    private CartService cartService = new HttpSessionCartService();
    @Captor
    private ArgumentCaptor<BigDecimal> cartPriceCaptor;

    private Phone testPhone;

    @Before
    public void setup() {
        testPhone = new Phone();
        testPhone.setPrice(new BigDecimal("1"));
        when(cartPriceCalculationService.calculateCartPrice(any(Cart.class))).thenReturn(new BigDecimal("2"));
        when(quantityValidator.isValid(anyLong(), anyLong())).thenReturn(true);
    }

    @Test
    public void shouldAddPhoneToCartItemsListIfPhoneIsNotInCartAndCountCartPrice() throws OutOfStockException {
        List<CartItem> cartItems = new ArrayList<>();
        when(cart.getCartItems()).thenReturn(cartItems);

        cartService.addPhone(1L, 1L);

        verify(cart).setCartPrice(cartPriceCaptor.capture());

        assertThat(cartPriceCaptor.getValue().doubleValue()).isEqualTo(2);
        assertThat(cartItems).hasSize(1)
                .contains(new CartItem(1L, 1L));
    }

    @Test
    public void shouldUpdatePhoneWhichIsAlreadyInCart() throws OutOfStockException {
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
    public void shouldUpdateCartAddCountCartPrice() throws OutOfStockException {
        List<CartItem> cartItems = new ArrayList<>();
        when(cart.getCartItems()).thenReturn(cartItems);
        Map<Long, Long> items = new HashMap<>();
        items.put(1L, 1L);
        items.put(2L, 1L);

        cartService.update(items);

        verify(cart, times(1)).setCartPrice(cartPriceCaptor.capture());

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
}
