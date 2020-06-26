package com.es.core.converters;

import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class IdToPhoneConverter implements Converter<String, Phone> {

    @Autowired
    private PhoneService phoneService;

    @Override
    public Phone convert(String aString) {
        return phoneService.getPhone(Long.parseLong(aString));
    }
}
