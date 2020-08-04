package com.es.core.order.quickorder;

import com.es.core.order.OutOfStockException;
import org.springframework.validation.Errors;

public interface QuickOrderService {
    QuickOrder getEmptyQuickOrder();
    void addItemsToCart(QuickOrder quickOrder, Errors errors) throws OutOfStockException;
}
