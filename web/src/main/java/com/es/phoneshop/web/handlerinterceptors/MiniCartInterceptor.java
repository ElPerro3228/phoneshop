package com.es.phoneshop.web.handlerinterceptors;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.cart.MiniCart;
import com.es.core.services.MiniCartService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MiniCartInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private MiniCartService miniCartService;
    @Resource
    private CartService cartService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MiniCart miniCart = miniCartService.createMiniCart(cartService.getCart());
        request.setAttribute("miniCart", miniCart);
    }
}
