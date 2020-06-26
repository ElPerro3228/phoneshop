package com.es.core.validators;

import com.es.core.cart.CartPageDTO;
import com.es.core.model.phone.AbstractIntegrationTest;
import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CartPageDTOValidatorTest extends AbstractIntegrationTest {
    @Autowired
    private CartPageDataValidator cartPageDataValidator;
    @Autowired
    private ConversionService conversionService;

    @Test
    public void shouldNotRejectValuesIfTheyCorrect() {
        Map<Phone, Long> cartItems = new HashMap<>();
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        cartItems.put(phone1, 1L);
        cartItems.put(phone2, 1L);
        CartPageDTO cartPageDTO = new CartPageDTO();
        cartPageDTO.setCartItems(cartItems);
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(cartPageDTO, "cartPageDTO");
        beanPropertyBindingResult.initConversion(conversionService);
        Errors errors = beanPropertyBindingResult;
        cartPageDataValidator.validate(cartPageDTO, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    public void shouldRejectValueWithNegativeQuantity() {
        Map<Phone, Long> cartItems = new HashMap<>();
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        cartItems.put(phone1, -1L);
        cartItems.put(phone2, 1L);
        CartPageDTO cartPageDTO = new CartPageDTO();
        cartPageDTO.setCartItems(cartItems);
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(cartPageDTO, "cartPageDTO");
        beanPropertyBindingResult.initConversion(conversionService);
        Errors errors = beanPropertyBindingResult;
        cartPageDataValidator.validate(cartPageDTO, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("cartItems[1001]")).isNotNull();
        assertThat(errors.getFieldErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("cartItems[1001]").getCode()).isEqualTo("validation.cartpage.quantity");
    }

    @Test
    public void shouldRejectQuantityWhichIsOutOfStock() {
        Map<Phone, Long> cartItems = new HashMap<>();
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        cartItems.put(phone1, 1000L);
        cartItems.put(phone2, 1L);
        CartPageDTO cartPageDTO = new CartPageDTO();
        cartPageDTO.setCartItems(cartItems);
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(cartPageDTO, "cartPageDTO");
        beanPropertyBindingResult.initConversion(conversionService);
        Errors errors = beanPropertyBindingResult;
        cartPageDataValidator.validate(cartPageDTO, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldError("cartItems[1001]")).isNotNull();
        assertThat(errors.getFieldErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("cartItems[1001]").getCode()).isEqualTo("validation.outOfStock");
    }

    @Test
    public void shouldRejectManyValuesWithWrongParameters() {
        Map<Phone, Long> cartItems = new HashMap<>();
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        cartItems.put(phone1, 1000L);
        cartItems.put(phone2, -1L);
        CartPageDTO cartPageDTO = new CartPageDTO();
        cartPageDTO.setCartItems(cartItems);
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(cartPageDTO, "cartPageDTO");
        beanPropertyBindingResult.initConversion(conversionService);
        Errors errors = beanPropertyBindingResult;
        cartPageDataValidator.validate(cartPageDTO, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getFieldErrorCount()).isEqualTo(2);
        assertThat(errors.getFieldError("cartItems[1001]")).isNotNull();
        assertThat(errors.getFieldError("cartItems[1001]").getCode()).isEqualTo("validation.outOfStock");
        assertThat(errors.getFieldError("cartItems[1002]")).isNotNull();
        assertThat(errors.getFieldError("cartItems[1002]").getCode()).isEqualTo("validation.cartpage.quantity");
    }
}
