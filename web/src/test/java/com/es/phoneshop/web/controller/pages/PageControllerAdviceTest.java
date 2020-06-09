package com.es.phoneshop.web.controller.pages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class PageControllerAdviceTest {

    @Mock
    private ProductListPageController controller;

    @Test
    public void testHandleJdbcPhoneDaoException() throws Exception {
        PageControllerAdvice controllerAdvice = new PageControllerAdvice();
        MockMvc mockMvc = standaloneSetup(controller)
                .setControllerAdvice(controllerAdvice)
                .build();
        when(controller.searchProductList(anyInt(), anyString(), anyString(), any(), any())).thenThrow(new DataRetrievalFailureException("null value"));

        mockMvc.perform(get("/productList")
                        .param("page", "1")
                        .param("query", "ARCHOS")
                        .param("field", "price")
                        .param("order", "ASC"))
                .andExpect(view().name("errors/errorPage"));
    }
}
