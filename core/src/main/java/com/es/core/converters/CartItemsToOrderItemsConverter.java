package com.es.core.converters;

import com.es.core.cart.CartItem;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemsToOrderItemsConverter implements Converter<List<CartItem>, List<OrderItem>> {

    @Autowired
    private PhoneService phoneService;

    @Override
    public List<OrderItem> convert(List<CartItem> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cartItems) {
            Phone phone = phoneService.getPhone(cartItem.getPhoneId());
            orderItems.add(new OrderItem(phone, cartItem.getQuantity()));
        }
        return orderItems;
    }

}
