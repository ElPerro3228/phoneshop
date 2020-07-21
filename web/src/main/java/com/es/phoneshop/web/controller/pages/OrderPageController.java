package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.core.services.CartPageDataService;
import com.es.core.validators.OrderItemsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Resource
    private CartPageDataService cartPageDataService;

    private static final Logger logger = LoggerFactory.getLogger(OrderPageController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        Cart cart = cartService.getCart();
        model.addAttribute("cartDTO", cartPageDataService.createCartPageData(cart));
        model.addAttribute("order", new Order());
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid @ModelAttribute("order") Order userForm, Errors errors) throws OutOfStockException {
        Cart cart = cartService.getCart();
        Order order = orderService.createOrder(cart);
        setUserFormInformation(userForm, order);
        orderItemsValidator.validate(order, errors);
        if (errors.hasErrors()) {
            return "order";
        }
        orderService.placeOrder(order);
        logger.info("order {} was saved", order.getId());
        return "redirect:orderOverview/" + order.getUuid().toString();
    }

    private void setUserFormInformation(Order userForm, Order order) {
        order.setFirstName(userForm.getFirstName());
        order.setLastName(userForm.getLastName());
        order.setContactPhoneNo(userForm.getContactPhoneNo());
        order.setDeliveryAddress(userForm.getDeliveryAddress());
    }
}
