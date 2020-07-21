package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper extends BeanPropertyRowMapper<Order> {

    public OrderRowMapper() {
        super(Order.class);
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Order order = super.mapRow(rs, rowNumber);
        order.setStatus(OrderStatus.valueOf(rs.getString("statusId")));
        return order;
    }
}
