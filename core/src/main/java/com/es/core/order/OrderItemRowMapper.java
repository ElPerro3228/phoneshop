package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.services.PhoneService;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    @Resource
    private PhoneService phoneService;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong("id"));
        orderItem.setPhone(phoneService.getPhone(resultSet.getLong("phoneId")));
        orderItem.setQuantity(resultSet.getLong("quantity"));
        return orderItem;
    }
}
