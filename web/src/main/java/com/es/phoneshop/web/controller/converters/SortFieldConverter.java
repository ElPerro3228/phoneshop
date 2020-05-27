package com.es.phoneshop.web.controller.converters;

import com.es.core.model.phone.SortField;
import org.springframework.core.convert.converter.Converter;

public class SortFieldConverter implements Converter<String, SortField> {
    @Override
    public SortField convert(String s) {
        try {
            return SortField.valueOf(s);
        } catch (IllegalArgumentException e) {
            return SortField.PRICE;
        }
    }
}
