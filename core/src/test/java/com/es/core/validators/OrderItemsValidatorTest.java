package com.es.core.validators;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemsValidatorTest {

    @Mock
    private QuantityValidator quantityValidator;
    @InjectMocks
    private OrderItemsValidator orderItemsValidator;

    @Test
    public void shouldNotRejectOrderItemsWithCorrectQuantities() {
        when(quantityValidator.isValid(anyLong(), anyLong())).thenReturn(true);
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        orderItems.add(new OrderItem(phone1, order, 1L));
        orderItems.add(new OrderItem(phone2, order, 1L));
        order.setOrderItems(orderItems);
        Errors errors = new BeanPropertyBindingResult(order, "order");
        orderItemsValidator.validate(order, errors);
        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    public void shouldRejectOrderItemsWithIncorrectQuantities() {
        when(quantityValidator.isValid(eq(1001L), anyLong())).thenReturn(true);
        when(quantityValidator.isValid(eq(1002L), anyLong())).thenReturn(false);
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        Phone phone1 = new Phone();
        phone1.setId(1001L);
        Phone phone2 = new Phone();
        phone2.setId(1002L);
        orderItems.add(new OrderItem(phone1, order, 1L));
        orderItems.add(new OrderItem(phone2, order, 1L));
        order.setOrderItems(orderItems);
        Errors errors = new BeanPropertyBindingResult(order, "order");
        orderItemsValidator.validate(order, errors);
        assertThat(errors.hasErrors()).isTrue();
    }
}
