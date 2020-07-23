package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/spring-security.xml", "classpath:dispatcher-servlet.xml"})
@WebAppConfiguration(value = "classpath:web.xml")
public class OrdersPageControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Autowired
    private OrderDao orderDao;

    private MockMvc mvc;
    private Order order;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
        order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setStatus(OrderStatus.NEW);
        order.setSubtotal(new BigDecimal("1"));
        order.setOrderItems(new ArrayList<>());
    }

    @Test
    public void testGetOrders() throws Exception {
        mvc.perform(get("/admin/orders")
                .with(csrf())
                .with(user("mkyong").password("123456").roles("USER")))
                .andExpect(view().name("adminOrdersPage"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    public void testGetOrder() throws Exception {
        orderDao.saveOrder(order);
        mvc.perform(get("/admin/orders/1")
                .with(csrf())
                .with(user("mkyong").password("123456").roles("USER")))
                .andExpect(view().name("adminOrderPage"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        orderDao.saveOrder(order);
        mvc.perform(post("/admin/orders/1")
                .with(csrf())
                .with(user("mkyong").password("123456").roles("USER")))
                .andExpect(view().name("adminOrderPage"))
                .andExpect(model().attributeExists("order"));
        Order updatedOrder = orderDao.getOrderById(1L).get();
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    public void shouldDenyAccessToAdminPages() throws Exception {
        orderDao.saveOrder(order);
        mvc.perform(post("/admin/orders/1")
                .with(csrf())
                .with(anonymous()))
                .andExpect(redirectedUrl("http://localhost/login"));
        mvc.perform(get("/admin/orders/1")
                .with(csrf())
                .with(anonymous()))
                .andExpect(redirectedUrl("http://localhost/login"));
        mvc.perform(get("/admin/orders")
                .with(csrf())
                .with(anonymous()))
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
