package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.order.OutOfStockException;
import com.es.core.services.CartPriceCalculationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private CartPriceCalculationService cartPriceCalculationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        Map<Phone, Long> cartItems = cartService.getCartItems(cart);
        BigDecimal cartPrice = cartPriceCalculationService.calculateCartPrice(cart);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartPrice", cartPrice);
        return "cartPage";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart() throws OutOfStockException {
        cartService.update(null);
    }
}
