package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    List<Phone> searchForPhones(int offset, int limit, String searchQuery, String sortField, SortOrder sortOrder);
    int getPhonesNumber();
}
