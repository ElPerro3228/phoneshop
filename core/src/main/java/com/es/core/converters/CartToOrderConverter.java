package com.es.core.converters;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.services.CartPriceCalculationService;
import com.es.core.services.PhoneService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CartToOrderConverter implements Converter<Cart, Order> {

    @Resource
    private CartPriceCalculationService cartPriceCalculationService;
    @Resource
    private PhoneService phoneService;
    @Resource
    private CartService cartService;

    @Override
    public Order convert(Cart cart) {
        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setSubtotal(cartPriceCalculationService.calculateSubtotalPrice(cart));
        order.setDeliveryPrice(cartPriceCalculationService.getDeliveryPrice());
        order.setTotalPrice(cartService.getCart().getCartPrice());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()) {
            Phone phone = phoneService.getPhone(cartItem.getPhoneId());
            orderItems.add(new OrderItem(phone, order, cartItem.getQuantity()));
        }
        order.setOrderItems(orderItems);
        order.setStatus(OrderStatus.NEW);
        return order;
    }

    @Resource
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
