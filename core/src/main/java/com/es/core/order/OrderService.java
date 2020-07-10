package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(Cart cart);
    Order updateOrderItems(Cart cart, Order order);
    void placeOrder(Order order) throws OutOfStockException;
    Order getOrderByUUID(UUID uuid);
    Order getOrderById(Long id);
    List<OrderItem> getOrderItems(Long id);
}
