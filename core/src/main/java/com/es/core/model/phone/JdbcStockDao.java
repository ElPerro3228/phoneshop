package com.es.core.model.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class JdbcStockDao implements StockDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("stockBeanPropertyRowMapper")
    private BeanPropertyRowMapper<Stock> stockBeanPropertyRowMapper;

    @Override
    public Stock getStock(Long phoneId) {
        return jdbcTemplate.queryForObject("select stock from stocks where phoneId = " + phoneId + ";", stockBeanPropertyRowMapper);
    }
}
