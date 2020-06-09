package com.es.core.services;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

@Service
public class DefaultProductPageDataService implements ProductPageDataService {
    @Resource(name = "properties")
    private Properties properties;

    @Override
    public List<String> getSortFields() {
        List<String> sortFields = new ArrayList<>();
        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String propertyName = enumeration.nextElement();
            String propertyValue = properties.getProperty(propertyName);
            if (propertyName.startsWith("sortField.")) {
                sortFields.add(propertyValue);
            }
        }
        return sortFields;
    }
}
