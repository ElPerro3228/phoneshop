package com.es.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DefaultPropertyService implements PropertyService {
    @Value("${sort.fields}")
    private String[] sortFields;
    @Value("${sort.defaultField}")
    private String defaultField;

    @Override
    public List<String> getSortFields() {
        return Arrays.asList(sortFields);
    }

    @Override
    public String getProperty(String name) {
        if (Arrays.asList(sortFields).contains(name)){
            return name;
        } else {
            return defaultField;
        }
    }
}
