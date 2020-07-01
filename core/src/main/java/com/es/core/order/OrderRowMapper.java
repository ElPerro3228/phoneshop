package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrderRowMapper extends BeanPropertyRowMapper<Order> {

    private Map<Long, OrderStatus> statusMap;

    public OrderRowMapper() {
        super(Order.class);
        statusMap = new HashMap<>();
        statusMap.put(1L, OrderStatus.NEW);
        statusMap.put(2L, OrderStatus.DELIVERED);
        statusMap.put(3L, OrderStatus.REJECTED);
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Order order = super.mapRow(rs, rowNumber);
        order.setStatus(statusMap.get(rs.getLong("statusId")));
        return order;
    }
}
