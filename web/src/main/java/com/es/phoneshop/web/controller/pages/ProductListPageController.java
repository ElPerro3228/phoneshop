package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.SortOrder;
import com.es.core.services.PhoneService;
import com.es.core.services.PropertyService;
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
    private PropertyService propertyService;

    @RequestMapping(method = RequestMethod.GET)
    public String searchProductList(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "query", defaultValue = "") String query,
                                    @RequestParam(value = "field", defaultValue = "price") String sortField,
                                    @RequestParam(value = "order", defaultValue = "") SortOrder sortOrder,
                                    Model model) {
        ProductPageData productPageData = new ProductPageData();
        productPageData.setSortFields(propertyService.getSortFields());
        productPageData.setPhones(phoneService.getPhonesPage(page, query, sortField, sortOrder));
        productPageData.setPagesNumber(phoneService.getPagesNumber());
        productPageData.setCurrentPage(page);
        model.addAttribute("pageBean", productPageData);
        return "productList";
    }
}
