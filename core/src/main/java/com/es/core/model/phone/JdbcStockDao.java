package com.es.core.model.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class JdbcStockDao implements StockDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private StockRowMapper stockRowMapper;

    private static final String UPDATE_QUERY = "update stocks set stock = :stock, reserved = :reserved " +
            "where phoneId = :phoneId;";

    @Override
    public Stock getStock(Long phoneId) {
        return jdbcTemplate.queryForObject("select * from stocks where phoneId = " + phoneId + ";", stockRowMapper);
    }

    @Override
    public void updateStock(Stock stock) {
        namedParameterJdbcTemplate.update(UPDATE_QUERY, createSqlParameterSource(stock));
    }

    private SqlParameterSource createSqlParameterSource(Stock stock) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("phoneId", stock.getPhone().getId());
        sqlParameterSource.addValue("stock", stock.getStock());
        sqlParameterSource.addValue("reserved", stock.getReserved());
        return sqlParameterSource;
    }
}
