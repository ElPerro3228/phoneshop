package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;

import java.util.List;

public interface PhoneService {
    int getPagesNumber();
    List<Phone> getPhonesPage(int page, String query, String sortField, SortOrder sortOrder);
    Phone getPhone(Long phoneId);
}
