package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.core.validators.OrderItemsValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;
    @Resource
    private OrderItemsValidator orderItemsValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        Cart cart = cartService.getCart();
        Order order = orderService.createOrder(cart);
        model.addAttribute("order", order);
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid Order order, Errors errors) throws OutOfStockException {
        orderItemsValidator.validate(order, errors);
        if (errors.hasErrors()) {
            return "order";
        }
        orderService.placeOrder(order);
        return "redirect:orderOverview/" + order.getUuid().toString();
    }
}
