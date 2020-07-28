package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOrder(@PathVariable("id") Long id, @RequestBody OrderStatus orderStatus) throws OutOfStockException {
        orderService.updateStatus(id, orderStatus);
        return new ResponseEntity(HttpStatus.OK);
    }
}
