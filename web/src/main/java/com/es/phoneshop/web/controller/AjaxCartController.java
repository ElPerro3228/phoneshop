package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemValidationException;
import com.es.core.cart.MiniCart;
import com.es.core.services.MiniCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Autowired
    private MiniCartService miniCartService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public MiniCart addPhone(@Valid @RequestBody CartItem cartItem, Errors errors){
        if (!errors.hasErrors()) {
            return miniCartService.updateCartAndReturnMiniCart(cartItem);
        } else {
            throw new CartItemValidationException(createMessage(errors));
        }
    }

    private String createMessage(Errors errors) {
        String message = "";
        for (ObjectError error : errors.getAllErrors()) {
            message += error.getDefaultMessage() + ", ";
        }
        message = message.substring(0, message.length() - 2);
        return message;
    }
}
