package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.converters.CartItemToOrderItemConverter;
import com.es.core.converters.CartToOrderConverter;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Autowired
    private CartToOrderConverter cartToOrderConverter;
    @Resource
    private StockService stockService;
    @Autowired
    private CartItemToOrderItemConverter cartItemToOrderItemConverter;

    @Override
    public Order createOrder(Cart cart) {
        return cartToOrderConverter.convert(cart);
    }

    @Override
    public Order updateOrderItems(Cart cart, Order order) {
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> cartItemToOrderItemConverter.convert(cartItem))
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.saveOrder(order);
        for (OrderItem orderItem : order.getOrderItems()) {
            stockService.updateStock(orderItem.getPhone().getId(), orderItem.getQuantity().intValue());
        }
    }

    @Override
    public Order getOrderByUUID(UUID uuid) {
        Optional<Order> order = orderDao.getOrderByUUID(uuid);
        return order.orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderDao.getOrderById(id);
        return order.orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public List<OrderItem> getOrderItems(Long id) {
        return orderDao.getOrderItemsByOrderId(id);
    }
}
