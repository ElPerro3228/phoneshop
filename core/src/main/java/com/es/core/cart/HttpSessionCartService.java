package com.es.core.cart;

import com.es.core.order.OutOfStockException;
import com.es.core.services.CartPriceCalculationService;
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
    private CartPriceCalculationService cartPriceCalculationService;
    @Autowired
    private QuantityValidator quantityValidator;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        addOrUpdateCartItem(phoneId, quantity);
    }

    private Optional<CartItem> findCartItem(Long phoneId) {
        return cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getPhoneId().equals(phoneId))
                    .findFirst();
    }

    @Override
    public void update(Map<Long, Long> items) throws OutOfStockException {
        for (Map.Entry<Long, Long> entry : items.entrySet()) {
            addOrUpdateCartItem(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhoneId().equals(phoneId));
    }

    private void addOrUpdateCartItem(Long phoneId, Long quantity) throws OutOfStockException {
        Optional<CartItem> optionalCartItem = findCartItem(phoneId);
        if (!quantityValidator.isValid(phoneId, quantity)) {
            throw new OutOfStockException("Invalid quantity");
        }
        if (optionalCartItem.isPresent()) {
            updateExistingItem(optionalCartItem.get(), quantity);
        } else {
            addNewItem(phoneId, quantity);
        }
        cart.setCartPrice(cartPriceCalculationService.calculateCartPrice(cart));
    }

    private void updateExistingItem(CartItem cartItem, Long quantity) {
        cartItem.setQuantity(quantity);
    }

    private void addNewItem(Long phoneId, Long quantity) {
        cart.getCartItems().add(new CartItem(phoneId, quantity));
    }
}
