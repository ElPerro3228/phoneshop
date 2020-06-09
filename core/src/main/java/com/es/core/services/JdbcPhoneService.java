package com.es.core.services;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class JdbcPhoneService implements PhoneService {

    @Value("${page.limit}")
    private int limit;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> getPhoneList(int page, String query, String sortField, SortOrder sortOrder) {
        int offset = (page - 1) * limit;
        MapSqlParameterSource sqlParameterSource = createMapSqlParameterSource(query);
        query = createSearchQuery(sqlParameterSource.getValues().size());
        return phoneDao.searchForPhones(offset, limit, query, sortField, sortOrder, sqlParameterSource);
    }

    @Override
    public int getPagesNumber() {
        int pagesNumber = phoneDao.getPhonesNumber() / limit;
        if ((phoneDao.getPhonesNumber() % limit) != 0) {
            pagesNumber += 1;
        }
        return pagesNumber;
    }

    private String createSearchQuery(int wordsCount) {
        String query = "";
        for (int i = 0; i < wordsCount; i++) {
            query += "brand like :word" + i + " or model like :word" + i + " or ";
        }
        query = query.substring(0, query.length() - 3);
        return query;
    }

    private MapSqlParameterSource createMapSqlParameterSource(String query) {
        String[] words = query.split(" ");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        for (int i = 0; i < words.length; i++) {
            sqlParameterSource.addValue("word" + i, "%" + words[i] + "%");
        }
        return sqlParameterSource;
    }
}
