package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartPageData;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.order.OutOfStockException;
import com.es.core.services.CartPageDataService;
import com.es.core.services.CartPriceCalculationService;
import com.es.core.validators.CartPageDataValidationException;
import com.es.core.validators.CartPageDataValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private CartPriceCalculationService cartPriceCalculationService;
    @Resource
    private CartPageDataValidator validator;
    @Resource
    private CartPageDataService cartPageDataService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cartPageData", cartPageDataService.createCartPageData(cart.getCartItems()));
        fillModel(model, cart);
        return "cartPage";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@ModelAttribute CartPageData cartPageData, Model model, Errors errors) throws OutOfStockException {
        validator.validate(cartPageData, errors);
        Cart cart = cartService.getCart();
        if (!errors.hasErrors()) {
            cartService.update(cartPageData.getCartItems());
            return "redirect:cartPage";
        }
        fillModel(model, cart);
        return "cartPage";
    }

    private void fillModel(Model model, Cart cart) {
        List<Phone> phones = cartService.getPhones(cart);
        BigDecimal cartPrice = cartPriceCalculationService.calculateCartPrice(cart);
        model.addAttribute("phones", phones);
        model.addAttribute("cartPrice", cartPrice);
    }
}
