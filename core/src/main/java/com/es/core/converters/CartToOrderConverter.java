package com.es.core.converters;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.services.CartPriceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CartToOrderConverter implements Converter<Cart, Order> {

    @Resource
    private CartPriceCalculationService cartPriceCalculationService;
    @Resource
    private CartService cartService;
    @Autowired
    private CartItemToOrderItemConverter cartItemToOrderItemConverter;

    @Override
    public Order convert(Cart cart) {
        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        cart.setCartPrice(cartPriceCalculationService.calculateCartPrice(cart));
        order.setSubtotal(cart.getCartPrice());
        order.setDeliveryPrice(cartPriceCalculationService.getDeliveryPrice());
        order.setTotalPrice(cartPriceCalculationService.calculateTotalPrice(cart));
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> cartItemToOrderItemConverter.convert(cartItem))
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        order.setStatus(OrderStatus.NEW);
        return order;
    }

    @Resource
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
