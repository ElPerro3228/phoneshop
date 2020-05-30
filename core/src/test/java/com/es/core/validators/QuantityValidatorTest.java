package com.es.core.validators;

import com.es.core.cart.CartItem;
import com.es.core.model.phone.StockDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ContextConfiguration("classpath:context/applicationContext-core.xml")
@PropertySource("classpath:conf/application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class QuantityValidatorTest {

    @Autowired
    private StockDao stockDao;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void shouldReturnTrueIfQuantityIsLessThanStock() {
        QuantityValidator quantityValidator = new QuantityValidator();
        quantityValidator.setStockDao(stockDao);
        quantityValidator.initialize(new QuantityConstraintTestClass());
        CartItem cartItem = new CartItem(1001L, 1L);
        boolean isValid = quantityValidator.isValid(cartItem, constraintValidatorContext);
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldReturnFalseIfQuantityIsGreaterThanStock() {
        QuantityValidator validator = new QuantityValidator();
        validator.setStockDao(stockDao);
        validator.initialize(new QuantityConstraintTestClass());
        CartItem cartItem = new CartItem(1001L, 100L);
        boolean isValid = validator.isValid(cartItem, constraintValidatorContext);
        assertThat(isValid).isFalse();
    }

    private class QuantityConstraintTestClass implements QuantityConstraint {

        @Override
        public String message() {
            return "Invalid quantity";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public String phoneId() {
            return "phoneId";
        }

        @Override
        public String quantity() {
            return "quantity";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }
}
