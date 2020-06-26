package com.es.core.validators;

import com.es.core.model.phone.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class QuantityValidatorTest extends AbstractIntegrationTest {

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
