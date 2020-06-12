package com.es.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPropertyService implements PropertyService {
    @Value("#{'${sort.fields}'.split(',')}")
    private List<String> sortFields;
    @Value("${sort.defaultField}")
    private String defaultField;

    @Override
    public List<String> getSortFields() {
        return sortFields;
    }

    @Override
    public String getProperty(String name) {
        if (sortFields.contains(name)){
            return name;
        } else {
            return defaultField;
        }
    }
}
