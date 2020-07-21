package com.es.core.validators;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class OrderItemsValidator implements Validator {

    @Autowired
    private QuantityValidator quantityValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Order order = (Order) o;
        for (OrderItem orderItem : order.getOrderItems()) {
            if (!quantityValidator.isValid(orderItem.getPhone().getId(), orderItem.getQuantity())) {
                errors.rejectValue("orderItems", "validation.orderItems", "Out of stock");
            }
        }
    }
}
