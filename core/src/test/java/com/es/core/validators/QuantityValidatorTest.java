package com.es.core.validators;

import com.es.core.cart.CartItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ContextConfiguration("classpath:context/applicationContext-core.xml")
@PropertySource("classpath:conf/application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class QuantityValidatorTest {

    @Autowired
    private QuantityValidator quantityValidator;

    @Test
    public void shouldReturnTrueIfQuantityIsLessThanStock() {
        boolean isValid = quantityValidator.isValid(1001L, 1L);
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldReturnFalseIfQuantityIsGreaterThanStock() {
        boolean isValid = quantityValidator.isValid(1001L, 100L);
        assertThat(isValid).isFalse();
    }
}
