package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    void saveOrder(Order order);
    Optional<Order> getOrderByUUID(UUID uuid);
    Optional<Order> getOrderById(Long id);
    Optional<List<OrderItem>> getOrderItemsByOrderId(Long id);
}
