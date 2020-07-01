package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.util.UUID;

@Component
@Transactional
public class JdbcOrderDao implements OrderDao{

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderItemRowMapper orderItemRowMapper;

    @Autowired
    private OrderRowMapper orderRowMapper;

    private static final String INSERT_ORDER = "insert into orders(id, uuid, subtotal, deliveryPrice, totalPrice, " +
            "firstName, lastName, deliveryAddress, contactPhoneNo, statusId) " +
            "values(:id, :uuid, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, :deliveryAddress, " +
            ":contactPhoneNo, :statusId);";
    private static final String INSERT_ORDER_ITEM = "insert into orderItems(id, phoneId, orderId, quantity) " +
            "values(:id, :phoneId, :orderId, :quantity);";
    private static final String UPDATE_ORDER = "update orders set " +
            "subtotal = :subtotal, deliveryPrice = :deliveryPrice, totalPrice = :totalPrice, firstName = :firstName, " +
            "lastName = :lastName, deliveryAddress = :deliveryAddress, contactPhoneNo = :contactPhoneNo, statusId = :statusId " +
            "where id = :id;";
    private static final String UPDATE_ORDER_ITEM = "update orderItems set " +
            "phoneId = :phoneId, orderId = :orderId, quantity = :quantity where id = :id;";

    @Override
    public void saveOrder(Order order) {
        if (order.getId() == null) {
            insertOrder(order);
        } else {
            updateOrder(order);
        }
    }

    private void insertOrder(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_ORDER, createSqlParameterSourceForOrder(order), keyHolder);
        order.setId(keyHolder.getKey().longValue());
        for (OrderItem orderItem : order.getOrderItems()) {
            namedParameterJdbcTemplate.update(INSERT_ORDER_ITEM, createSqlParameterSourceForOrderItem(orderItem), keyHolder);
            orderItem.setId(keyHolder.getKey().longValue());
        }
    }

    private void updateOrder(Order order) {
        namedParameterJdbcTemplate.update(UPDATE_ORDER, createSqlParameterSourceForOrder(order));
        for (OrderItem orderItem : order.getOrderItems()) {
            namedParameterJdbcTemplate.update(UPDATE_ORDER_ITEM, createSqlParameterSourceForOrderItem(orderItem));
        }
    }

    @Override
    public Optional<Order> getOrderByUUID(UUID uuid) {
        try {
            Order order = jdbcTemplate.queryForObject("select * from orders where uuid = '" + uuid.toString() + "';", orderRowMapper);
            List<OrderItem> orderItems = jdbcTemplate.query("select * from orderItems where orderId = " + order.getId() +";", orderItemRowMapper);
            order.setOrderItems(orderItems);
            return Optional.of(order);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private SqlParameterSource createSqlParameterSourceForOrder(Order order) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("id", order.getId());
        sqlParameterSource.addValue("uuid", order.getUuid().toString());
        sqlParameterSource.addValue("subtotal", order.getSubtotal().doubleValue());
        sqlParameterSource.addValue("deliveryPrice", order.getDeliveryAddress());
        sqlParameterSource.addValue("totalPrice", order.getTotalPrice());
        sqlParameterSource.addValue("firstName", order.getFirstName());
        sqlParameterSource.addValue("lastName", order.getLastName());
        sqlParameterSource.addValue("deliveryAddress", order.getDeliveryAddress());
        sqlParameterSource.addValue("contactPhoneNo", order.getContactPhoneNo());
        sqlParameterSource.addValue("statusId", order.getStatus().getId());
        return sqlParameterSource;
    }

    private SqlParameterSource createSqlParameterSourceForOrderItem(OrderItem orderItem) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("id", orderItem.getId());
        sqlParameterSource.addValue("phoneId", orderItem.getPhone().getId());
        sqlParameterSource.addValue("orderId", orderItem.getOrder().getId());
        sqlParameterSource.addValue("quantity", orderItem.getQuantity());
        return sqlParameterSource;
    }
}
