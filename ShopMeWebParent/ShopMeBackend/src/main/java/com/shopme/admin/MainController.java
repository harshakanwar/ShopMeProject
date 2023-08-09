package com.shopme.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String viewHomePhae() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
