package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.converters.CartToOrderConverter;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.AbstractIntegrationTest;
import com.es.core.model.phone.StockDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Transactional
public class OrderServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private CartToOrderConverter cartToOrderConverter;

    private Cart cart;

    @Before
    public void setup() {
        cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1001L, 1L));
        cartItems.add(new CartItem(1002L, 1L));
        cartItems.add(new CartItem(1003L, 1L));
        cart.setCartItems(cartItems);
        CartService mockCartService = Mockito.mock(CartService.class);
        cartToOrderConverter.setCartService(mockCartService);
        when(mockCartService.getCart()).thenReturn(cart);
    }

    @Test
    public void shouldCreateOrder() throws OutOfStockException {
        Order order = orderService.createOrder(cart);
        orderService.placeOrder(order);
        assertThat(order).isNotNull();
        assertThat(order.getId())
                .isNotNull()
                .matches(id -> id > 0);
    }

    @Test
    public void shouldReturnNotNullOrder() throws OutOfStockException {
        Order order = orderService.createOrder(cart);
        orderService.placeOrder(order);
        Order returnedOrder = orderService.getOrderByUUID(order.getUuid());
        assertThat(returnedOrder.getId()).isEqualTo(order.getId());
        assertThat(returnedOrder.getUuid())
                .isNotNull()
                .isEqualTo(order.getUuid());
    }

    @Test(expected = OrderNotFoundException.class)
    public void shouldThrowOrderNotFoundException() {
        orderService.getOrderByUUID(UUID.randomUUID());
    }

    @Test
    public void shouldUpdateOrderAndStocks() throws OutOfStockException {
        Order order = orderService.createOrder(cart);
        List<Integer> oldQuantities = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            oldQuantities.add(stockDao.getStock(orderItem.getPhone().getId()).getStock());
        }
        order.setFirstName("Stas");
        order.setStatus(OrderStatus.DELIVERED);
        orderService.placeOrder(order);
        Order updatedOrder = orderService.getOrderByUUID(order.getUuid());
        List<Integer> newQuantities = new ArrayList<>();
        for (OrderItem orderItem : updatedOrder.getOrderItems()) {
            newQuantities.add(stockDao.getStock(orderItem.getPhone().getId()).getStock());
        }
        assertThat(updatedOrder).isNotNull();
        assertThat(updatedOrder.getFirstName()).isEqualTo("Stas");
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(newQuantities.toArray()).doesNotContain(oldQuantities);
    }
}
