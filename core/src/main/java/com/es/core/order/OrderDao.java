package com.es.core.order;

import com.es.core.model.order.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    void saveOrder(Order order);
    Optional<Order> getOrderByUUID(UUID uuid);
}
