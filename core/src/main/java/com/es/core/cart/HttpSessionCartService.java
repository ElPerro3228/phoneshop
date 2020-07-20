package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.order.OutOfStockException;
import com.es.core.services.CartPriceCalculationService;
import com.es.core.services.PhoneService;
import com.es.core.validators.QuantityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {

    @Autowired
    private Cart cart;

    private CartPriceCalculationService cartPriceCalculationService;
    @Autowired
    private QuantityValidator quantityValidator;
    @Autowired
    private PhoneService phoneService;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        addOrUpdateCartItem(phoneId, quantity);
        cartPriceCalculationService.setPrices(cart);
    }

    private Optional<CartItem> findCartItem(Long phoneId) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getPhoneId().equals(phoneId))
                .findFirst();
    }

    @Override
    public void update(Map<Long, Long> items) throws OutOfStockException {
        for (Map.Entry<Long, Long> entry : items.entrySet()) {
            if (entry.getValue() != 0) {
                addOrUpdateCartItem(entry.getKey(), entry.getValue());
            } else {
                remove(entry.getKey());
            }
        }
        cartPriceCalculationService.setPrices(cart);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhoneId().equals(phoneId));
        cartPriceCalculationService.setPrices(cart);
    }

    private void addOrUpdateCartItem(Long phoneId, Long quantity) throws OutOfStockException {
        Optional<CartItem> optionalCartItem = findCartItem(phoneId);
        if (!quantityValidator.isValid(phoneId, quantity)) {
            throw new OutOfStockException("out of stock", "validation.outOfStock");
        }
        if (optionalCartItem.isPresent()) {
            updateExistingItem(optionalCartItem.get(), quantity);
        } else {
            addNewItem(phoneId, quantity);
        }
    }

    @Override
    public List<Phone> getPhones(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        return cartItems.stream()
                .map(cartItem -> phoneService.getPhone(cartItem.getPhoneId()))
                .collect(Collectors.toList());
    }

    private void updateExistingItem(CartItem cartItem, Long quantity) {
        cartItem.setQuantity(quantity);
    }

    private void addNewItem(Long phoneId, Long quantity) {
        cart.getCartItems().add(new CartItem(phoneId, quantity));
    }

    @Autowired
    public void setCartPriceCalculationService(CartPriceCalculationService cartPriceCalculationService) {
        this.cartPriceCalculationService = cartPriceCalculationService;
    }
}

