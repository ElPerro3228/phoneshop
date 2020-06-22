package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import com.es.core.model.phone.Phone;
import com.es.core.services.MiniCartService;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private CartService cartService;
    @Autowired
    private MiniCartService miniCartService;

    @RequestMapping(value = "/{phoneId}")
    public String getPhoneDetailsPage(
            @PathVariable(value = "phoneId") Long phoneId, Model model
    ) {
        Phone phone = phoneService.getPhone(phoneId);
        model.addAttribute("phone", phone);
        return "phoneDetails";
    }
}
