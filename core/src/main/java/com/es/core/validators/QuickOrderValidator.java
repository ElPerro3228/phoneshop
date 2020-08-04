package com.es.core.validators;

import com.es.core.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.order.quickorder.QuickOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Service
public class QuickOrderValidator implements Validator {

    @Autowired
    private QuantityValidator quantityValidator;
    @Autowired
    private PhoneDao phoneDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return QuickOrder.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuickOrder quickOrder = (QuickOrder) o;
        List<CartItem> orderItems = quickOrder.getOrderItems();
        int index = 0;
        for (CartItem orderItem : orderItems) {
            if ((orderItem.getPhoneId() == null) && (orderItem.getQuantity() == null)) {
                continue;
            }
            if (orderItem.getPhoneId() == null) {
                orderItem.setPhoneId(-1L);
            }
            validatePhoneId(errors, index, orderItem);
            validateQuantity(errors, index, orderItem);
            index++;
        }
    }

    private void validateQuantity(Errors errors, int index, CartItem orderItem) {
        if (!alreadyHasQuantityError(errors, index)) {
            if (isValidQuantity(orderItem)) {
                errors.rejectValue("orderItems[" + index + "].quantity", "validation.outOfStock", "Out of stock");
            }
        }
    }

    private void validatePhoneId(Errors errors, int index, CartItem orderItem) {
        if (orderItem.getPhoneId() < 0) {
            errors.rejectValue("orderItems[" + index + "].phoneId", "validation.cartpage.quantity", "Must be more or equal to 0");
        }
        Optional<Phone> phone = phoneDao.get(orderItem.getPhoneId());
        if (!phone.isPresent()) {
            errors.rejectValue("orderItems[" + index + "].phoneId", "validation.cartpage.null", "not exist");
        }
        if (phone.isPresent()) {
            if (phone.get().getPrice() == null) {
                errors.rejectValue("orderItems[" + index + "].phoneId", "validation.cartpage.null", "not exist");
            }
        }
    }

    private boolean alreadyHasQuantityError(Errors errors, int index) {
        if (errors.getFieldError("orderItems[" + index + "].quantity") != null) {
            return true;
        }
        return false;
    }

    private boolean isValidQuantity(CartItem orderItem) {
        return !quantityValidator.isValid(orderItem.getPhoneId(), orderItem.getQuantity());
    }
}
