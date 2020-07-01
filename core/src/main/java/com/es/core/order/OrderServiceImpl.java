package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.services.CartPriceCalculationService;
import com.es.core.services.PhoneService;
import com.es.core.services.StockService;
import com.es.core.validators.QuantityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private CartPriceCalculationService cartPriceCalculationService;
    @Resource
    private PhoneService phoneService;
    @Resource
    private StockService stockService;
    @Autowired
    private QuantityValidator quantityValidator;

    @Override
    public Order createOrder(Cart cart) {
        Order order = convertCartToOrder(cart);
        order.setStatus(OrderStatus.NEW);
        orderDao.saveOrder(order);
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        orderDao.saveOrder(order);
        for (OrderItem orderItem : order.getOrderItems()) {
            if (!quantityValidator.isValid(orderItem.getPhone().getId(), orderItem.getQuantity())) {
                throw new OutOfStockException();
            }
            stockService.updateStock(orderItem.getPhone().getId(), orderItem.getQuantity().intValue());
        }
    }

    @Override
    public Order getOrderByUUID(UUID uuid) {
        Optional<Order> order = orderDao.getOrderByUUID(uuid);
        return order.orElseThrow(OrderNotFoundException::new);
    }

    private Order convertCartToOrder(Cart cart) {
        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setSubtotal(cartPriceCalculationService.calculateSubtotalPrice(cart));
        order.setDeliveryPrice(cartPriceCalculationService.getDeliveryPrice());
        order.setTotalPrice(cartPriceCalculationService.calculateCartPrice(cart));
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()) {
            Phone phone = phoneService.getPhone(cartItem.getPhoneId());
            orderItems.add(new OrderItem(phone, order, cartItem.getQuantity()));
        }
        order.setOrderItems(orderItems);
        return order;
    }
}
