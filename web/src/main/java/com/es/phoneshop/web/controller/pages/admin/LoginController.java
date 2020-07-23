package com.es.phoneshop.web.controller.pages.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Locale;

@Controller
public class LoginController {
    @Resource
    private MessageSourceAccessor messageSourceAccessor;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model, Locale locale) {

        if (error != null) {
            model.addAttribute("error", messageSourceAccessor.getMessage("authentication.fails", locale));
        }

        if (logout != null) {
            model.addAttribute("msg", messageSourceAccessor.getMessage("authentication.logout", locale));
        }

        return "login";

    }
}
