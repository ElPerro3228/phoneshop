package com.es.core.model.phone;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
}
