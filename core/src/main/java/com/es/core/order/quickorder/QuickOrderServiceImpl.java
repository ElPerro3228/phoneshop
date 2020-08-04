package com.es.core.order.quickorder;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.order.OrderItem;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;

@Service
public class QuickOrderServiceImpl implements QuickOrderService {

    @Value("${quickOrder.size}")
    private int quickOrderSize;

    @Autowired
    private CartService cartService;

    @Override
    public QuickOrder getEmptyQuickOrder() {
        ArrayList<CartItem> orderItems = new ArrayList<>();
        for (int i = 0; i < quickOrderSize; i++) {
            orderItems.add(new CartItem());
        }
        return new QuickOrder(orderItems);
    }

    @Override
    public void addItemsToCart(QuickOrder quickOrder, Errors errors) throws OutOfStockException {
        int index = 0;
        for (CartItem cartItem : quickOrder.getOrderItems()) {
            if ((errors.getFieldError("orderItems[" + index + "].phoneId") == null) && (errors.getFieldError("orderItems[" + index + "].quantity") == null)) {
                cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            }
            index++;
        }
    }
}
