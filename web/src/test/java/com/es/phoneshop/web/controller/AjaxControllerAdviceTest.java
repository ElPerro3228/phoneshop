package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemValidationException;
import com.es.core.order.OutOfStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class AjaxControllerAdviceTest {
    @Mock
    private Errors errors;
    @Mock
    private ObjectError error;
    @Mock
    private AjaxCartController ajaxCartController;

    @Test
    public void shouldHandleCartItemValidationException() throws Exception {
        List<ObjectError> errorsList = new ArrayList<>();
        errorsList.add(error);

        AjaxControllerAdvice ajaxControllerAdvice = new AjaxControllerAdvice();
        MockMvc mockMvc = standaloneSetup(ajaxCartController)
                .setControllerAdvice(ajaxControllerAdvice)
                .build();
        String cartItem = "{\"phoneId\":1,\"quantity\":-1}";

        when(ajaxCartController.addPhone(any(), any())).thenThrow(new CartItemValidationException(errors));
        when(errors.getAllErrors()).thenReturn(errorsList);
        when(error.getDefaultMessage()).thenReturn("negative number");

        mockMvc.perform(post("/ajaxCart")
                .content(cartItem)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("negative number")));
    }

    @Test
    public void shouldHandleOutOfStockException() throws Exception {
        AjaxControllerAdvice ajaxControllerAdvice = new AjaxControllerAdvice();
        MockMvc mockMvc = standaloneSetup(ajaxCartController)
                .setControllerAdvice(ajaxControllerAdvice)
                .build();
        String cartItem = "{\"phoneId\":1,\"quantity\":1}";

        when(ajaxCartController.addPhone(any(), any())).thenThrow(new OutOfStockException("too much"));

        mockMvc.perform(post("/ajaxCart")
                .content(cartItem)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("too much")));
    }
}
