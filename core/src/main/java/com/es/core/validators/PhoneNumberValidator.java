package com.es.core.validators;

import com.es.core.constraints.PhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        Phonenumber.PhoneNumber phoneNumber;
        try {
            phoneNumber = PhoneNumberUtil.getInstance().parse(phone, LocaleContextHolder.getLocale().toString());
        } catch (NumberParseException e) {
            return false;
        }
        return PhoneNumberUtil.getInstance().isValidNumber(phoneNumber);
    }
}
