package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.AbstractIntegrationTest;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class JdbcOrderDaoIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private OrderDao orderDao;

    private Order order;

    @Before
    public void setup() {
        order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        orderItems.add(new OrderItem(phone1, order, 1L));
        orderItems.add(new OrderItem(phone2, order, 1L));
        order.setUuid(UUID.randomUUID());
        order.setOrderItems(orderItems);
        order.setSubtotal(new BigDecimal(1000L));
        order.setDeliveryPrice(new BigDecimal(5L));
        order.setTotalPrice(new BigDecimal(1005L));
        order.setStatus(OrderStatus.NEW);
    }

    @Test
    public void shouldInsertOrder() {
        orderDao.saveOrder(order);
        assertThat(order.getId())
                .isNotNull()
        .isGreaterThan(0L);
    }

    @Test
    public void shouldReturnOrderByUUID() {
        orderDao.saveOrder(order);
        Order returnedOrder = orderDao.getOrderByUUID(order.getUuid()).get();
        assertThat(returnedOrder).isNotNull();
        assertThat(returnedOrder.getId()).isEqualTo(order.getId());
        assertThat(returnedOrder.getTotalPrice().doubleValue()).isEqualTo(order.getTotalPrice().doubleValue());
        assertThat(returnedOrder.getStatus()).isEqualTo(order.getStatus());
    }

    @Test
    public void shouldUpdateExistingOrder() {
        assertThat(order.getFirstName()).isNull();
        orderDao.saveOrder(order);
        order.setFirstName("Stas");
        orderDao.saveOrder(order);
        Order updatedOrder = orderDao.getOrderByUUID(order.getUuid()).get();
        assertThat(updatedOrder.getFirstName()).isEqualTo(order.getFirstName());
    }
}
