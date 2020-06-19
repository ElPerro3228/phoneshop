package com.es.core.validators;

import com.es.core.cart.CartPageData;
import com.es.core.model.phone.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CartPageDataValidatorTest extends AbstractIntegrationTest {
    @Autowired
    private CartPageDataValidator cartPageDataValidator;

    @Test
    public void shouldNotRejectValuesIfTheyCorrect() {
        Map<Long, Long> cartItems = new HashMap<>();
        cartItems.put(1001L, 1L);
        cartItems.put(1002L, 1L);
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(cartItems);
        Errors errors = new BeanPropertyBindingResult(cartPageData, "cartPageData");
        cartPageDataValidator.validate(cartPageData, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    public void shouldRejectValueWithNegativeQuantity() {
        Map<Long, Long> cartItems = new HashMap<>();
        cartItems.put(1001L, -1L);
        cartItems.put(1002L, 1L);
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(cartItems);
        Errors errors = new BeanPropertyBindingResult(cartPageData, "cartPageData");
        cartPageDataValidator.validate(cartPageData, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("cartItems[1001]")).isNotNull();
        assertThat(errors.getFieldErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("cartItems[1001]").getCode()).isEqualTo("validation.moreThanOne");
    }

    @Test
    public void shouldRejectQuantityWhichIsOutOfStock() {
        Map<Long, Long> cartItems = new HashMap<>();
        cartItems.put(1001L, 1000L);
        cartItems.put(1002L, 1L);
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(cartItems);
        Errors errors = new BeanPropertyBindingResult(cartPageData, "cartPageData");
        cartPageDataValidator.validate(cartPageData, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("cartItems[1001]")).isNotNull();
        assertThat(errors.getFieldErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("cartItems[1001]").getCode()).isEqualTo("validation.outOfStock");
    }

    @Test
    public void shouldRejectManyValuesWithWrongParameters() {
        Map<Long, Long> cartItems = new HashMap<>();
        cartItems.put(1001L, 1000L);
        cartItems.put(1002L, -1L);
        CartPageData cartPageData = new CartPageData();
        cartPageData.setCartItems(cartItems);
        Errors errors = new BeanPropertyBindingResult(cartPageData, "cartPageData");
        cartPageDataValidator.validate(cartPageData, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldErrorCount()).isEqualTo(2);
        assertThat(errors.getFieldError("cartItems[1001]")).isNotNull();
        assertThat(errors.getFieldError("cartItems[1001]").getCode()).isEqualTo("validation.outOfStock");
        assertThat(errors.getFieldError("cartItems[1002]")).isNotNull();
        assertThat(errors.getFieldError("cartItems[1002]").getCode()).isEqualTo("validation.moreThanOne");
    }
}
