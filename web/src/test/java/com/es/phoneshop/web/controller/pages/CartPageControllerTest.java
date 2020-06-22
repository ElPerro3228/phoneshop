package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockDao;
import com.es.core.services.CartPageDataService;
import com.es.core.services.CartPriceCalculationService;
import com.es.core.services.DefaultCartPageDataService;
import com.es.core.validators.CartPageDataValidator;
import com.es.core.validators.QuantityValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CartPageControllerTest {
    @Mock
    private StockDao stockDao;
    @Mock
    private CartService cartService;
    @Spy
    private QuantityValidator quantityValidator = new QuantityValidator();
    @Spy
    private CartPageDataValidator cartPageDataValidator = new CartPageDataValidator();
    @Spy
    private CartPageDataService cartPageDataService = new DefaultCartPageDataService();
    @InjectMocks
    private CartPageController cartPageController = new CartPageController();

    @Before
    public void setup() {
        quantityValidator.setStockDao(stockDao);
        cartPageDataValidator.setQuantityValidator(quantityValidator);
        List<CartItem>  cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, 1L));
        cartItems.add(new CartItem(2L, 1L));
        Cart cart = new Cart();
        cart.setCartItems(cartItems);
        when(cartService.getCart()).thenReturn(cart);

        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        phone1.setId(1L);
        phone2.setId(2L);
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);
        when(cartService.getPhones(eq(cart))).thenReturn(phones);
    }

    @Test
    public void testGetCart() throws Exception {
        MockMvc mockMvc = standaloneSetup(cartPageController).build();

        mockMvc.perform(get("/cart"))
                .andExpect(view().name("cartPage"))
                .andExpect(model().attributeExists("cartPageData"))
                .andExpect(model().attributeExists("phones"))
                .andExpect(model().attributeExists("cartPrice"));
    }

    @Test
    public void shouldRedirectToCartIfUpdateIsSuccessful() throws Exception {
        Stock stock = new Stock();
        stock.setStock(1000);
        when(stockDao.getStock(any())).thenReturn(stock);
        MockMvc mockMvc = standaloneSetup(cartPageController).build();

        mockMvc.perform(post("/cart")
                        .param("cartItems[1]", "1")
                        .param("cartItems[2]", "1"))
                .andExpect(redirectedUrl("cart"));
    }

    @Test
    public void shouldReturnErrorMessagesIfUpdateIsNotSuccessful() throws Exception {
        Stock stock = new Stock();
        stock.setStock(1);
        when(stockDao.getStock(eq(1L))).thenReturn(stock);
        when(stockDao.getStock(eq(2L))).thenReturn(stock);
        MockMvc mockMvc = standaloneSetup(cartPageController).build();

        mockMvc.perform(post("/cart")
                .param("cartItems[1]", "100")
                .param("cartItems[2]", "-1"))
                .andExpect(view().name("cartPage"))
                .andExpect(model().attributeHasFieldErrors("cartPageData", "cartItems[1]", "cartItems[2]"))
                .andExpect(model().attributeHasFieldErrorCode("cartPageData", "cartItems[1]", "validation.outOfStock"))
                .andExpect(model().attributeHasFieldErrorCode("cartPageData", "cartItems[2]", "validation.moreThanOne"));
    }
}
