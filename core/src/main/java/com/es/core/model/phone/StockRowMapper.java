package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StockRowMapper extends BeanPropertyRowMapper<Stock> {

    @Resource
    private PhoneDao phoneDao;

    public StockRowMapper() {
        super(Stock.class);
    }

    @Override
    public Stock mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Stock stock = super.mapRow(rs, rowNumber);
        Optional<Phone> phone = phoneDao.get(rs.getLong("phoneId"));
        stock.setPhone(phone.orElseThrow(PhoneNotFoundException::new));
        return stock;
    }
}
