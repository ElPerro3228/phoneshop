package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

//    @RequestMapping(method = RequestMethod.GET)
//    public String showProductList(Model model) {
//        model.addAttribute("phones", phoneDao.findAll(10, 10));
//        return "productList";
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String searchProductList(@RequestParam(value = "query", defaultValue = "") String query,
                                    @RequestParam(value = "field", defaultValue = "") SortField sortField,
                                    @RequestParam(value = "order", defaultValue = "") SortOrder sortOrder,
                                    Model model) {
        model.addAttribute("phones", phoneDao.searchForPhones(0, 10, query, sortField, sortOrder));
        return "productList";
    }
}
