package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Cart addPhone(@Valid @RequestBody CartItem cartItem, Errors errors) {
        if (!errors.hasErrors()) {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            return new Cart(cartService.getCart().getCartItems(), cartService.getCart().getSubTotalPrice());
        } else {
            throw new CartItemValidationException(errors.getAllErrors().get(0).getDefaultMessage());
        }
    }
}
