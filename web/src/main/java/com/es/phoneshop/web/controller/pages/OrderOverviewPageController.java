package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public String getOrderOverview(@PathVariable("uuid") String uuid, Model model) {
        Order order = orderService.getOrderByUUID(UUID.fromString(uuid));
        model.addAttribute("order", order);
        return "orderOverview";
    }
}
