package com.es.core.services;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import com.es.core.model.phone.PhoneDao;
import com.es.core.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultMiniCartService implements MiniCartService{

    private CartService cartService;
    private PhoneDao phoneDao;
    private MiniCart miniCart;

    @Autowired
    public void setMiniCart(MiniCart miniCart) {
        this.miniCart = miniCart;
    }

    @Autowired
    public void setPhoneDao(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public MiniCart updateCartAndReturnMiniCart(CartItem cartItem) {
        try {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            updateMiniCart(cartService.getCart());
            return miniCart;
        } catch (OutOfStockException e) {
            throw new CartItemValidationException(e.getMessage());
        }
    }

    @Override
    public BigDecimal countCartPrice(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        double price = cartItems.stream()
                .filter(cartItem -> phoneDao.get(cartItem.getPhoneId()).isPresent())
                .mapToDouble(cartItem -> phoneDao.get(cartItem.getPhoneId()).get().getPrice().doubleValue() * cartItem.getQuantity())
                .sum();
        return new BigDecimal(price);
    }

    private long countQuantity(Cart cart) {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    private void updateMiniCart(Cart cart) {
        long quantity = countQuantity(cart);
        BigDecimal cartPrice = cart.getCartPrice();
        miniCart.setQuantity(quantity);
        miniCart.setCartPrice(cartPrice);
    }
}
