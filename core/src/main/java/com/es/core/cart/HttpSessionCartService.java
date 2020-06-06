package com.es.core.cart;

import com.es.core.order.OutOfStockException;
import com.es.core.services.MiniCartService;
import com.es.core.validators.QuantityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private Cart cart;
    @Autowired
    private MiniCartService miniCartService;
    @Autowired
    private QuantityValidator quantityValidator;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        Optional<CartItem> optionalCartItem = findCartItem(phoneId);
        addOrUpdateCartItem(phoneId, quantity, optionalCartItem);
    }

    private Optional<CartItem> findCartItem(Long phoneId) {
        return cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getPhoneId().equals(phoneId))
                    .findFirst();
    }

    @Override
    public void update(Map<Long, Long> items) throws OutOfStockException {
        for (Long phoneId : items.keySet()) {
            Long quantity = items.get(phoneId);
            Optional<CartItem> optionalCartItem = findCartItem(phoneId);
            addOrUpdateCartItem(phoneId, quantity, optionalCartItem);
        }
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhoneId().equals(phoneId));
    }

    private void addOrUpdateCartItem(Long phoneId, Long quantity, Optional<CartItem> optionalCartItem) throws OutOfStockException {
        if (!quantityValidator.isValid(phoneId, quantity)) {
            throw new OutOfStockException("Invalid quantity");
        }
        if (optionalCartItem.isPresent()) {
            optionalCartItem.get().setQuantity(quantity);
        } else {
            cart.getCartItems().add(new CartItem(phoneId, quantity));
        }
        cart.setCartPrice(miniCartService.countCartPrice(cart));
    }
}
