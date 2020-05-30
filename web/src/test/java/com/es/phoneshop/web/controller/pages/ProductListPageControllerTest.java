package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import com.es.core.services.PhoneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageControllerTest {
    @Mock
    private PhoneService phoneService;
    @InjectMocks
    private ProductListPageController controller = new ProductListPageController();

    @Test
    public void testShowProductList() throws Exception {
        List<Phone> expectedPhones = createProductList();
        when(phoneService.getPhoneList(1, "ARCHOS", SortField.PRICE, SortOrder.ASC)).thenReturn(expectedPhones);

        MockMvc mockMvc = standaloneSetup(controller).setSingleView(
                new InternalResourceView("WEB-INF/pages/productLis.jsp")
        ).build();

        mockMvc.perform(get("/productList")
                .param("page", "1")
                .param("query", "ARCHOS")
                .param("field", "PRICE")
                .param("order", "ASC"))
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("phones"))
                .andExpect(model().attribute("phones", hasItems(expectedPhones.toArray())));
    }

    private List<Phone> createProductList() {
        List<Phone> phones = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            phones.add(new Phone());
        }
        return phones;
    }
}
