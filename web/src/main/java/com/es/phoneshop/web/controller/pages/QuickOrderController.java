package com.es.phoneshop.web.controller.pages;

import com.es.core.order.OutOfStockException;
import com.es.core.order.quickorder.QuickOrder;
import com.es.core.order.quickorder.QuickOrderService;
import com.es.core.validators.QuickOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/quickOrder")
public class QuickOrderController {

    @Autowired
    private QuickOrderService quickOrderService;
    @Autowired
    private QuickOrderValidator quickOrderValidator;

    @RequestMapping
    public String getQuickOrderPage(Model model) {
        model.addAttribute("quickOrder", quickOrderService.getEmptyQuickOrder());
        return "quickOrderPage";
    }

    @PostMapping
    public String postQuickOrder(@ModelAttribute @Valid QuickOrder quickOrder, Errors errors) throws OutOfStockException {
        quickOrderValidator.validate(quickOrder, errors);
        quickOrderService.addItemsToCart(quickOrder, errors);
        return "quickOrderPage";
    }
}
