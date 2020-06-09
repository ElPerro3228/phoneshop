package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import com.es.core.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private ProductListPageBean pageBean;

    @RequestMapping(method = RequestMethod.GET)
    public String searchProductList(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "query", defaultValue = "") String query,
                                    @RequestParam(value = "field", defaultValue = "") String sortField,
                                    @RequestParam(value = "order", defaultValue = "") SortOrder sortOrder,
                                    Model model) {
        pageBean.setPhones(phoneService.getPhoneList(page, query, sortField, sortOrder));
        pageBean.setPagesNumber(phoneService.getPagesNumber());
        pageBean.setCurrentPage(page);
        model.addAttribute("pageBean", pageBean);
        return "productList";
    }
}
