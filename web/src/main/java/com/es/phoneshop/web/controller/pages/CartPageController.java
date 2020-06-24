package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartPageDTO;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.order.OutOfStockException;
import com.es.core.services.CartPageDataService;
import com.es.core.validators.CartPageDataValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private CartPageDataValidator cartPageDataValidator;
    @Resource
    private CartPageDataService cartPageDataService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cartPageDTO", cartPageDataService.createCartPageData(cart));
        return "cartPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateCart(CartPageDTO cartPageDTO, Model model, Errors errors) throws OutOfStockException {
        cartPageDataValidator.validate(cartPageDTO, errors);
        if (!errors.hasErrors()) {
            cartService.update(cartPageDataService.convert(cartPageDTO.getCartItems()));
            return "redirect:cart";
        }
        return "cartPage";
    }
}
