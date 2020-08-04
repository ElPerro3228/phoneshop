package com.es.core.order.quickorder;

import com.es.core.cart.CartItem;
import com.es.core.model.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class QuickOrder {
    private List<CartItem> orderItems;

    public QuickOrder() {
        orderItems = new ArrayList<>();
    }

    public QuickOrder(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }
}
