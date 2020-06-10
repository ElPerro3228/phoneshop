package com.es.core.services;

import java.util.List;

public interface PropertyService {
    List<String> getSortFields();
    String getProperty(String name);
}
