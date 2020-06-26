package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.PhoneNotFoundException;
import com.es.core.model.phone.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class JdbcPhoneService implements PhoneService {

    @Value("${page.limit}")
    private int limit;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> getPhonesPage(int page, String query, String sortField, SortOrder sortOrder) {
        int offset = (page - 1) * limit;
        return phoneDao.searchForPhones(offset, limit, query, sortField, sortOrder);
    }

    @Override
    public int getPagesNumber() {
        int pagesNumber = phoneDao.getPhonesNumber() / limit;
        if ((phoneDao.getPhonesNumber() % limit) != 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }

    @Override
    public Phone getPhone(Long phoneId) {
        Optional<Phone> phone = phoneDao.get(phoneId);
        return phone.orElseThrow(PhoneNotFoundException::new);
    }
}
