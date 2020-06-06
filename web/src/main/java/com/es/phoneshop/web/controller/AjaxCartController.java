package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import com.es.core.order.OutOfStockException;
import com.es.core.services.MiniCartService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MiniCartService miniCartService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public MiniCart addPhone(@Valid @RequestBody CartItem cartItem, Errors errors) {
        try {
            return getMiniCart(cartItem, errors);
        } catch (OutOfStockException e) {
            throw new CartItemValidationException(e.getMessage());
        }
    }

    private MiniCart getMiniCart(CartItem cartItem, Errors errors) throws OutOfStockException {
        if (!errors.hasErrors()) {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            return miniCartService.createMiniCart(cartService.getCart());
        } else {
            throw new CartItemValidationException(errors.getAllErrors().get(0).getDefaultMessage());
        }
    }
}
