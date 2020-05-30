package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class AjaxCartControllerTest {
    @Mock
    private CartService cartService;
    @InjectMocks
    private AjaxCartController controller = new AjaxCartController();

    @Test
    public void testAddPhone() throws Exception {
        List<CartItem> cartItems = new ArrayList<>();
        Cart cart = new Cart(cartItems, new BigDecimal("1"));
        when(cartService.getCart()).thenReturn(cart);

        MockMvc mockMvc = standaloneSetup(controller).build();
        ObjectMapper mapper = new ObjectMapper();
        String cartItem = mapper.writeValueAsString(new CartItem(1L, 1L));

        mockMvc.perform(post("/ajaxCart")
                .content(cartItem)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartPrice", is(1)));
    }

    @Test
    public void shouldThrowCartItemValidationExceptionIfErrorOccurs() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        String cartItem = "{\"phoneId\":1,\"quantity\":one}";

        mockMvc.perform(post("/ajaxCart")
                .content(cartItem)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
