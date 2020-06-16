package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortOrder;

import java.util.List;

public interface PhoneService {
    int getPagesNumber();
    List<Phone> getPhoneList(int page, String query, String sortField, SortOrder sortOrder);
}
