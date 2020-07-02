package com.es.core.converters;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class OrderIdToOrderItemsConverter implements Converter<String, List<OrderItem>> {

    @Autowired
    private OrderService orderService;

    @Override
    public List<OrderItem> convert(String id) {
        Long orderId = Long.parseLong(id);
        return orderService.getOrderById(orderId).getOrderItems();
    }
}
