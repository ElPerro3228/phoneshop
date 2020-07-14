package com.es.core.converters;

import com.es.core.cart.CartItem;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CartItemToOrderItemConverter implements Converter<CartItem, OrderItem> {

    @Autowired
    private PhoneService phoneService;

    @Override
    public OrderItem convert(CartItem cartItem) {
        Phone phone = phoneService.getPhone(cartItem.getPhoneId());
        OrderItem orderItem = new OrderItem(phone, cartItem.getQuantity());
        return orderItem;
    }

}
