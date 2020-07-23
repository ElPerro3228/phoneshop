package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "adminOrdersPage";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getOrder(@PathVariable("id") Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "adminOrderPage";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateOrder(@PathVariable("id") Long id, @RequestParam(name = "orderStatus", defaultValue = "DELIVERED") OrderStatus orderStatus, Model model) throws OutOfStockException {
        orderService.updateStatus(id, orderStatus);
        model.addAttribute("order", orderService.getOrderById(id));
        return "adminOrderPage";
    }
}
