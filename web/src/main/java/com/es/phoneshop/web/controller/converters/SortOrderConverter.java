package com.es.phoneshop.web.controller.converters;

import com.es.core.model.phone.SortOrder;
import org.springframework.core.convert.converter.Converter;

public class SortOrderConverter implements Converter<String, SortOrder> {
    @Override
    public SortOrder convert(String s) {
        try {
            return SortOrder.valueOf(s);
        } catch (IllegalArgumentException e) {
            return SortOrder.DESC;
        }
    }
}
