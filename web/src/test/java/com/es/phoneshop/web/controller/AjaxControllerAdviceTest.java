package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    private AjaxCartController ajaxCartController;

    @Test
    public void testHandleCartItemValidationException() throws Exception {
        AjaxControllerAdvice ajaxControllerAdvice = new AjaxControllerAdvice();
        MockMvc mockMvc = standaloneSetup(ajaxCartController)
                .setControllerAdvice(ajaxControllerAdvice)
                .build();
        String cartItem = "{\"phoneId\":1,\"quantity\":1}";

        when(ajaxCartController.addPhone(any(), any())).thenThrow(new CartItemValidationException("too much"));

        mockMvc.perform(post("/ajaxCart")
                .content(cartItem)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("too much")));
    }
}
