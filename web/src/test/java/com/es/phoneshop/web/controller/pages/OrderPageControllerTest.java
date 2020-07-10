package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.validators.OrderItemsValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderPageControllerTest {
    @Mock
    private OrderService orderService;
    @Mock
    private CartService cartService;
    @Mock
    private OrderItemsValidator orderItemsValidator;
    @InjectMocks
    private OrderPageController orderPageController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix("WEB-INF");
        viewResolver.setPrefix(".jsp");
        Cart cart = new Cart();
        when(cartService.getCart()).thenReturn(cart);
        when(orderService.createOrder(eq(cart))).thenReturn(new Order());

        mockMvc = standaloneSetup(orderPageController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetOrder() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    public void testPlaceOrder() throws Exception {
        Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        String uuid = UUID.randomUUID().toString();
        mockMvc.perform(post("/order")
                .sessionAttr("sessionOrder", order)
                .param("id", "1")
                .param("uuid", uuid)
                .param("subtotal", "1")
                .param("deliveryPrice", "1")
                .param("totalPrice", "1")
                .param("firstName", "1")
                .param("lastName", "1")
                .param("deliveryAddress", "1")
                .param("contactPhoneNo", "1")
                .param("status", "NEW"))
                .andExpect(redirectedUrl("orderOverview/" + uuid));
    }

    @Test
    public void shouldRejectOrder() throws Exception {
        Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        mockMvc.perform(post("/order")
                .sessionAttr("sessionOrder", order)
                .param("id", "1")
                .param("uuid", "1")
                .param("subtotal", "1")
                .param("deliveryPrice", "1")
                .param("totalPrice", "1")
                .param("firstName", "1")
                .param("lastName", "1")
                .param("deliveryAddress", "1")
                .param("contactPhoneNo", "1")
                .param("status", "NEW"))
                .andExpect(view().name("order"));
    }
}
