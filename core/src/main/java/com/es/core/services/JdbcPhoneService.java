package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JdbcPhoneService implements PhoneService {

    private static final int LIMIT = 10;

    @Resource
    PhoneDao phoneDao;

    @Override
    public List<Phone> getPhoneList(int page, String query, SortField sortField, SortOrder sortOrder) {
        int offset = (page - 1) * LIMIT;
        String[] words = query.split(" ");
        query = "";
        for (String word : words) {
            query += "brand like '%" + word + "%' or model like '%" + word + "%' or ";
        }
        query = query.substring(0, query.length() - 3);
        return phoneDao.searchForPhones(offset, LIMIT, query, sortField, sortOrder);
    }

    @Override
    public int getPagesNumber() {
        return phoneDao.getPhonesNumber() / LIMIT;
    }
}
