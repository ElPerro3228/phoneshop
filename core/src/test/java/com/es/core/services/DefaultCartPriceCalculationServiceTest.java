package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.model.phone.AbstractIntegrationTest;
import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultCartPriceCalculationServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CartPriceCalculationService cartPriceCalculationService;

    @Test
    public void shouldCorrectlyCountCartPrice() {
        Phone mockPhone = new Phone();
        mockPhone.setPrice(new BigDecimal("1"));
        Cart testCart = new Cart();
        testCart.getCartItems().add(new CartItem(1000L, 1L));
        testCart.getCartItems().add(new CartItem(1001L, 2L));
        testCart.getCartItems().add(new CartItem(1002L, 2L));

        BigDecimal price = cartPriceCalculationService.calculateCartPrice(testCart);

        assertThat(price).isEqualTo("55.0");
    }
}
