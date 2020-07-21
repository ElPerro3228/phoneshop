package com.es.core.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberValidatorTest {

    private PhoneNumberValidator phoneNumberValidator;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Before
    public void setup() {
        phoneNumberValidator = new PhoneNumberValidator();
    }

    @Test
    public void shouldReturnTrueIfPhoneIsValid() {
        String phoneNumber = "+375445357384";
        boolean result = phoneNumberValidator.isValid(phoneNumber, constraintValidatorContext);
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfPhoneIsNotValid() {
        String phoneNumber = "+37445357384";
        boolean result = phoneNumberValidator.isValid(phoneNumber, constraintValidatorContext);
        assertThat(result).isFalse();
    }
}
