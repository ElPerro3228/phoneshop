package com.es.core.model.phone;

import com.es.core.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class JdbcPhoneDao implements PhoneDao{
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private PropertyService propertyService;

    private final PhoneRowMapper phoneRowMapper;

    private static final String UPDATE_PHONE_BY_ID = "update phones set " +
            "brand = :brand, model = :model, price = :price, displaySizeInches = :displaySizeInches, weightGr = :weightGr, lengthMm = :lengthMm, " +
            "widthMm = :widthMm, heightMm = :heightMm, announced = :announced, deviceType = :deviceType, os = :os, displayResolution = :displayResolution, pixelDensity = :pixelDensity, " +
            "displayTechnology = :displayTechnology, backCameraMegapixels = :backCameraMegapixels, frontCameraMegapixels = :frontCameraMegapixels, ramGb = :ramGb, internalStorageGb = :internalStorageGb, " +
            "batteryCapacityMah = :batteryCapacityMah, talkTimeHours = :talkTimeHours, standByTimeHours = :standByTimeHours, bluetooth = :bluetooth, positioning = :positioning, " +
            "imageUrl = :imageUrl, description = :description where id = :id;";

    private static final String INSERT_QUERY = "insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, " +
            "widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, " +
            "batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) " +
            "values (:id, :brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, :heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity," +
            ":displayTechnology, :backCameraMegapixels, :frontCameraMegapixels, :ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, :standByTimeHours, :bluetooth, :positioning," +
            ":imageUrl, :description);";

    private static final String COUNT_PHONES_WITH_NOT_EMPTY_STOCK_AND_PRICE = "select count(*) from phones join stocks on id = phoneId " +
            "where stock > 0 and price > 0;";

    private static final String SEARCH_QUERY = "brand like :query or model like :query";

    @Autowired
    public JdbcPhoneDao(PhoneRowMapper phoneRowMapper) {
        this.phoneRowMapper = phoneRowMapper;
    }

    public Optional<Phone> get(final Long key) {
        Optional<Phone> phone;
        try {
            return phone = Optional.of(jdbcTemplate.queryForObject("select * from phones where id = " + key + ";", phoneRowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        if (phone.getId() == null) {
            insert(phone);
        } else {
            update(phone);
        }
    }

    private void update(Phone phone) {
        namedParameterJdbcTemplate.update(UPDATE_PHONE_BY_ID, createSqlParameterSource(phone));
    }

    private void insert(Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_QUERY, createSqlParameterSource(phone), keyHolder);
        phone.setId((Long) keyHolder.getKey());
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, phoneRowMapper);
    }

    @Override
    public List<Phone> searchForPhones(int offset, int limit, String searchQuery, String sortField, SortOrder sortOrder) {
        if ((offset < 0) || (limit < 0) || (searchQuery == null) || (sortField == null) || (sortOrder == null)) {
            throw new DataRetrievalFailureException("null parameter");
        }
        MapSqlParameterSource sqlParameterSource = createMapSqlParameterSource(searchQuery);
        return namedParameterJdbcTemplate.query("select * from phones " +
                "join stocks on id = phoneId  where (" + SEARCH_QUERY + ") and stock > 0 and price > 0" +
                "order by " + getSortField(sortField) + " " + sortOrder.getValue() +
                " limit " + limit + " offset " + offset + ";", sqlParameterSource , phoneRowMapper);
    }

    private String getSortField(String sortField) {
        return propertyService.getProperty(sortField);
    }

    @Override
    public int getPhonesNumber() {
        return jdbcTemplate.queryForObject(COUNT_PHONES_WITH_NOT_EMPTY_STOCK_AND_PRICE, Integer.class);
    }

    private SqlParameterSource createSqlParameterSource(Phone phone) {
        return new BeanPropertySqlParameterSource(phone);
    }

    private MapSqlParameterSource createMapSqlParameterSource(String query) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("query", "%" + query + "%");
        return sqlParameterSource;
    }
}
