package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartDTO;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.converters.IdToPhoneConverter;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockDao;
import com.es.core.services.CartPageDataService;
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
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CartPageControllerTest {

    private FormattingConversionService formattingConversionService;
    private List<Phone> phones;
    @Spy
    private IdToPhoneConverter idToPhoneConverter = new IdToPhoneConverter();
    @Mock
    private StockDao stockDao;
    @Mock
    private CartService cartService;
    @Spy
    private QuantityValidator quantityValidator = new QuantityValidator();
    @Spy
    private CartPageDataValidator cartPageDataValidator = new CartPageDataValidator();
    @Mock
    private CartPageDataService cartPageDataService = new DefaultCartPageDataService();
    @InjectMocks
    private CartPageController cartPageController = new CartPageController();

    private MockMvc mockMvc;

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
        phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);

        formattingConversionService = new FormattingConversionService();
        formattingConversionService.addConverter(idToPhoneConverter);
        mockMvc = standaloneSetup(cartPageController)
                .setConversionService(formattingConversionService)
                .build();
    }

    @Test
    public void testGetCart() throws Exception {
        when(cartPageDataService.createCartPageData(any())).thenReturn(new CartDTO());

        mockMvc.perform(get("/cart"))
                .andExpect(view().name("cartPage"))
                .andExpect(model().attributeExists("cartPageDTO"));
    }

    @Test
    public void shouldRedirectToCartIfUpdateIsSuccessful() throws Exception {
        Stock stock = new Stock();
        stock.setStock(1000);
        doReturn(phones.get(0)).when(idToPhoneConverter).convert(eq("1"));
        doReturn(phones.get(1)).when(idToPhoneConverter).convert(eq("2"));

        when(stockDao.getStock(any())).thenReturn(stock);

        mockMvc.perform(post("/cart")
                        .param("cartItems[1]", "1")
                        .param("cartItems[2]", "1")
                        .param("cartPrice", "100"))
                .andExpect(redirectedUrl("cart"));
    }

    @Test
    public void shouldReturnErrorMessagesIfUpdateIsNotSuccessful() throws Exception {
        Stock stock = new Stock();
        stock.setStock(1);
        when(stockDao.getStock(eq(1L))).thenReturn(stock);
        when(stockDao.getStock(eq(2L))).thenReturn(stock);
        doReturn(phones.get(0)).when(idToPhoneConverter).convert(eq("1"));
        doReturn(phones.get(1)).when(idToPhoneConverter).convert(eq("2"));

        mockMvc.perform(post("/cart")
                .param("cartItems[1]", "100")
                .param("cartItems[2]", "-1"))
                .andExpect(view().name("cartPage"))
                .andExpect(model().attributeHasFieldErrors("cartDTO", "cartItems[1]", "cartItems[2]"))
                .andExpect(model().attributeHasFieldErrorCode("cartDTO", "cartItems[1]", "validation.outOfStock"))
                .andExpect(model().attributeHasFieldErrorCode("cartDTO", "cartItems[2]", "validation.cartpage.quantity"));
    }
}
