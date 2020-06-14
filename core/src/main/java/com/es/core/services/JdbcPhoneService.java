package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JdbcPhoneService implements PhoneService {

    @Value("${page.limit}")
    private int limit;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> getPhoneList(int page, String query, String sortField, SortOrder sortOrder) {
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
}
