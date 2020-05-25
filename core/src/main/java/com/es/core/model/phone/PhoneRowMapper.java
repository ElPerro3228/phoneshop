package com.es.core.model.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Component
public class PhoneRowMapper extends BeanPropertyRowMapper<Phone> {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private BeanPropertyRowMapper beanPropertyRowMapper;

    public PhoneRowMapper() {
        super(Phone.class);
    }

    @Override
    public Phone mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Phone phone = super.mapRow(rs, rowNumber);
        List<Color> colors = jdbcTemplate.query("select colors.* from phone2color " +
                "join colors on colorId = colors.id where phoneId = " + phone.getId() + ";", beanPropertyRowMapper);
        phone.setColors(new HashSet<>(colors));
        return phone;
    }

    @Autowired
    public void setBeanPropertyRowMapper(BeanPropertyRowMapper beanPropertyRowMapper) {
        this.beanPropertyRowMapper = beanPropertyRowMapper;
    }
}
