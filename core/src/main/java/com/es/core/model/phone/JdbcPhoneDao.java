package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao{
    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String UPDATE_QUERY = "update phones set " +
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

    public Optional<Phone> get(final Long key) {
        Optional<Phone> phone = Optional.empty();
        try {
            phone = Optional.of(jdbcTemplate.queryForObject("select * from phones where id = " + key + ";", new PhoneRowMapper(Phone.class)));
        } finally {
            return phone;
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
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update(UPDATE_QUERY, createSqlParameterSource(phone));
    }

    private void insert(Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update(INSERT_QUERY, createSqlParameterSource(phone), keyHolder);
        phone.setId((Long) keyHolder.getKey());
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new PhoneRowMapper(Phone.class));
    }


    private SqlParameterSource createSqlParameterSource(Phone phone) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", phone.getId());
        parameterSource.addValue("brand", phone.getBrand());
        parameterSource.addValue("model", phone.getModel());
        parameterSource.addValue("price", phone.getPrice());
        parameterSource.addValue("displaySizeInches", phone.getDisplaySizeInches());
        parameterSource.addValue("weightGr", phone.getWeightGr());
        parameterSource.addValue("lengthMm", phone.getLengthMm());
        parameterSource.addValue("widthMm", phone.getWidthMm());
        parameterSource.addValue("heightMm", phone.getHeightMm());
        parameterSource.addValue("announced", phone.getAnnounced());
        parameterSource.addValue("deviceType", phone.getDeviceType());
        parameterSource.addValue("os", phone.getOs());
        parameterSource.addValue("displayResolution", phone.getDisplayResolution());
        parameterSource.addValue("pixelDensity", phone.getPixelDensity());
        parameterSource.addValue("displayTechnology", phone.getDisplayTechnology());
        parameterSource.addValue("backCameraMegapixels", phone.getBackCameraMegapixels());
        parameterSource.addValue("frontCameraMegapixels", phone.getFrontCameraMegapixels());
        parameterSource.addValue("ramGb", phone.getRamGb());
        parameterSource.addValue("batteryCapacityMah", phone.getBatteryCapacityMah());
        parameterSource.addValue("talkTimeHours", phone.getTalkTimeHours());
        parameterSource.addValue("standByTimeHours", phone.getStandByTimeHours());
        parameterSource.addValue("bluetooth", phone.getBluetooth());
        parameterSource.addValue("positioning", phone.getPositioning());
        parameterSource.addValue("imageUrl", phone.getImageUrl());
        parameterSource.addValue("description", phone.getDescription());
        parameterSource.addValue("internalStorageGb", phone.getInternalStorageGb());
        return parameterSource;
    }

    private class PhoneRowMapper extends BeanPropertyRowMapper<Phone> {

        public PhoneRowMapper(Class<Phone> mappedClass) {
            super(mappedClass);
        }

        @Override
        public Phone mapRow(ResultSet rs, int rowNumber) throws SQLException {
            Phone phone = super.mapRow(rs, rowNumber);
            List<Color> colors = jdbcTemplate.query("select colors.* from phone2color " +
                    "join colors on colorId = colors.id where phoneId = " + phone.getId() + ";", new BeanPropertyRowMapper(Color.class));
            phone.setColors(new HashSet<>(colors));
            return phone;
        }
    }
}
