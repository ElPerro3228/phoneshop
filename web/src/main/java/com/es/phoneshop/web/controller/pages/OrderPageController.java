package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.converters.CartItemToOrderItemConverter;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.core.validators.OrderItemsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;
    @Resource
    private OrderItemsValidator orderItemsValidator;
    @Autowired
    private CartItemToOrderItemConverter cartItemToOrderItemConverter;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        Cart cart = cartService.getCart();
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> cartItemToOrderItemConverter.convert(cartItem))
                .collect(Collectors.toList());
        Order order = new Order();
        order.setOrderItems(orderItems);
        model.addAttribute("order", order);
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
        return "redirect:orderOverview/" + order.getUuid().toString();
    }

    private void setUserFormInformation(Order userForm, Order order) {
        order.setFirstName(userForm.getFirstName());
        order.setLastName(userForm.getLastName());
        order.setContactPhoneNo(userForm.getContactPhoneNo());
        order.setDeliveryAddress(userForm.getDeliveryAddress());
    }
}
